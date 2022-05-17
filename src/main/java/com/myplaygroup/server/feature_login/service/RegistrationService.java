package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.repository.AppTokenRepository;
import com.myplaygroup.server.feature_login.request.EditProfileRequest;
import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.response.EditProfileResponse;
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

    public EditProfileResponse updateProfile(String username, UpdateProfileRequest request) {

        AppUser appUser = appUserService.loadUserByUsername(username);

        String encodedPassword = bCryptPasswordEncoder.encode(request.password);

        appUser.setPassword(encodedPassword);
        appUser.setProfileName(request.profileName);
        appUser.setPhoneNumber(request.phoneNumber);
        appUser.setEmail(request.email);
        appUser.setProfileCreated(true);

        appUserRepository.save(appUser);

        return new EditProfileResponse(
                username,
                appUser.getProfileName(),
                appUser.getPhoneNumber(),
                appUser.getEmail()
        );
    }

    public EditProfileResponse editProfile(String username, EditProfileRequest request)
    {
        AppUser appUser = appUserService.loadUserByUsername(username);
        if(!appUser.getProfileCreated()){
            throw new IllegalStateException("User profile hasn't been created");
        }

        if(request.password != null){
            String encodedPassword = bCryptPasswordEncoder.encode(request.password);
            appUser.setPassword(encodedPassword);
        }

        if(request.profileName != null){
            appUser.setProfileName(request.profileName);
        }

        if(request.email != null){
            appUser.setEmail(request.email);
        }

        if(request.phoneNumber != null){
            appUser.setPhoneNumber(request.phoneNumber);
        }

        appUserRepository.save(appUser);

        return new EditProfileResponse(
                username,
                appUser.getProfileName(),
                appUser.getPhoneNumber(),
                appUser.getEmail()
        );
    }
}
