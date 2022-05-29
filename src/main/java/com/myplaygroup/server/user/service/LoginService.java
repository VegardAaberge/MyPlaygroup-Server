package com.myplaygroup.server.user.service;

import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.model.LockedRefreshToken;
import com.myplaygroup.server.user.repository.LockedRefreshTokenRepository;
import com.myplaygroup.server.user.request.LoginRequest;
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

        Map<String, Object> tokens = authorizationService.getAccessAndRefreshToken(
                user,
                request.getRequestURL().toString()
        );

        tokenRepository.save(new LockedRefreshToken(
                userInfo.getToken(),
                userInfo.getUsername(),
                LocalDateTime.now()
        ));

        return tokens;
    }
}
