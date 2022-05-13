package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.request.LoginRequest;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorizationService authorizationService;

    public String authenticate(LoginRequest loginRequest) {
        AppUser appUser = appUserService.loadUserByUsername(loginRequest.username);

        if (!appUser.isAccountNonLocked()) {
            throw new IllegalStateException("Account is locked");
        }

        if (!appUser.isEnabled()) {
            throw new IllegalStateException("Account is not enabled");
        }

        if (!bCryptPasswordEncoder.matches(loginRequest.password, appUser.getPassword())) {
            throw new IllegalStateException("Password was incorrect");
        }

        return "Success";
    }

    public Map<String, String> refreshTokens(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

            String refresh_token = authorizationHeader.substring("Bearer ".length());
            String requestUrl = request.getRequestURL().toString();

            String username = authorizationService.getUsernameFromToken(refresh_token);
            AppUser user = appUserService.loadUserByUsername(username);

            return authorizationService.getAccessTokenFromRefreshToken(refresh_token, requestUrl, user);
        }else {
            throw new IllegalStateException("Refresh token is missing");
        }
    }
}
