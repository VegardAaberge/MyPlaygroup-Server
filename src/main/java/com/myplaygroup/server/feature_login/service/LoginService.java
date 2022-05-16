package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.model.LockedRefreshToken;
import com.myplaygroup.server.feature_login.repository.LockedRefreshTokenRepository;
import com.myplaygroup.server.feature_login.request.LoginRequest;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.security.model.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorizationService authorizationService;
    private final LockedRefreshTokenRepository tokenRepository;

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

    public Map<String, Object> refreshTokens(HttpServletRequest request) {

        UserInfo userInfo = authorizationService.getUserInfoFromRequest(request);
        AppUser user = appUserService.loadUserByUsername(userInfo.getUsername());

        if(tokenRepository.findByToken(userInfo.getToken()).isPresent()){
            throw new IllegalStateException("Token is locked");
        }

        Map<String, Object> tokens = authorizationService.getAccessTokenFromRefreshToken(
                userInfo.getToken(),
                request.getRequestURL().toString(),
                user
        );

        tokenRepository.save(new LockedRefreshToken(
                userInfo.getToken(),
                userInfo.getUsername(),
                LocalDateTime.now()
        ));

        return tokens;
    }
}
