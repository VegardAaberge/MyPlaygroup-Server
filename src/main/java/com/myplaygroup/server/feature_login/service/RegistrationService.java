package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.repository.AppTokenRepository;
import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.response.UpdateProfileResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.USER;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final static String PROFILE_NAME = "Profile name";
    private final static String PASSWORD = "Password";
    private final static String PHONE_NUMBER = "Phone number";
    private final static String EMAIL = "Email";

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final AppTokenRepository appTokenRepository;

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
                USER
        );
        appUserRepository.save(appUser);

        return "Registered user: " + request.username;
    }

    public UpdateProfileResponse updateProfile(String username, UpdateProfileRequest request) {

        AppUser appUser = appUserService.loadUserByUsername(username);

        String encodedPassword = bCryptPasswordEncoder.encode(request.password);

        appUser.setPassword(encodedPassword);
        appUser.setProfileName(request.profileName);
        appUser.setPhoneNumber(request.phoneNumber);
        appUser.setEmail(request.email);
        appUser.setProfileCreated(true);

        appUserRepository.save(appUser);

        return new UpdateProfileResponse(
                username,
                appUser.getProfileName(),
                appUser.getPhoneNumber(),
                appUser.getEmail()
        );
    }
}
