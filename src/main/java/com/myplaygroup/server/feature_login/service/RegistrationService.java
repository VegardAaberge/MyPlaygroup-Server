package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.model.AppToken;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.repository.AppTokenRepository;
import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.validator.EmailValidator;
import com.myplaygroup.server.feature_login.validator.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.*;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserRepository appUserRepository;
    private final AppTokenRepository appTokenRepository;

    private final EmailValidator emailValidator;
    private final PhoneNumberValidator phoneNumberValidator;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(RegistrationRequest request) {
        Optional<AppUser> appUserOptional = appUserRepository.findByUsername(request.username);

        if(appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            if(appUser.isEnabled()){
                throw new IllegalStateException("username already taken");
            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.password);

        AppUser appUser = new AppUser(
                request.username,
                encodedPassword,
                request.isAdmin ? ADMIN : USER
        );
        appUserRepository.save(appUser);

        return "Registered user: " + request.username;
    }

    public String updateProfile(String username,
                                String profileName,
                                String password,
                                String phoneNumber,
                                String email) {

        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if(isNullOrEmpty(profileName)){
            throw new IllegalStateException("Profile name must have a value");
        }

        if(isNullOrEmpty(password)){
            throw new IllegalStateException("password must have a value");
        }

        appUser.setProfileName(profileName);

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        appUser.setPassword(encodedPassword);

        if(!isNullOrEmpty(phoneNumber) && phoneNumberValidator.test(phoneNumber)){
            appUser.setPhoneNumber(phoneNumber);
        }

        if(!isNullOrEmpty(email) && emailValidator.test(email)){
            appUser.setEmail(email);
        }

        return "Updated profile: " + appUser.getUsername();
    }

    private boolean isNullOrEmpty(String newValue){
        return newValue == null || newValue.isEmpty();
    }
}
