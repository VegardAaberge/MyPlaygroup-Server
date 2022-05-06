package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppToken;
import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppTokenRepository;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.request.LoginRequest;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserRepository appUserRepository;
    private final AppTokenRepository appTokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String authenticate(LoginRequest loginRequest) {
        AppUser appUser = appUserRepository.findByUsername(loginRequest.username)
                .orElseThrow(() -> new UsernameNotFoundException("Username was not found"));

        if(appUser.isAccountNonLocked()){
            throw new IllegalStateException("Account is locked");
        }

        if(!appUser.isEnabled()){
            throw new IllegalStateException("Account is not enabled");
        }

       String encodedPassword = bCryptPasswordEncoder.encode(loginRequest.password);
       if(encodedPassword != appUser.getPassword()){
           throw new IllegalStateException("Password was incorrect");
       }

        return "Success";
    }
}
