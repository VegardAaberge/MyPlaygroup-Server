package com.myplaygroup.server.feature_login.service;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.model.AppToken;
import com.myplaygroup.server.feature_login.repository.AppUserRepository;
import com.myplaygroup.server.feature_login.repository.AppTokenRepository;
import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.validator.EmailValidator;
import com.myplaygroup.server.feature_login.validator.PasswordValidator;
import com.myplaygroup.server.feature_login.validator.PhoneNumberValidator;
import com.myplaygroup.server.feature_login.validator.ProfileNameValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.myplaygroup.server.feature_login.model.AppUser.UserRole.USER;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final static String PROFILE_NAME = "Profile name";
    private final static String PASSWORD = "Password";
    private final static String PHONE_NUMBER = "Phone number";
    private final static String EMAIL = "Email";

    private final AppUserRepository appUserRepository;
    private final AppTokenRepository appTokenRepository;

    private final ProfileNameValidator profileNameValidator;
    private final PasswordValidator passwordValidator;
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
                USER
        );
        appUserRepository.save(appUser);

        return "Registered user: " + request.username;
    }

    public String updateProfile(String username, UpdateProfileRequest request) {

        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Boolean isProfileNameValid = !isNullOrEmpty(request.profileName) && profileNameValidator.test(request.profileName);
        Boolean isPasswordValid = !isNullOrEmpty(request.password) && passwordValidator.test(request.password);
        Boolean isPhoneNumberValid = !isNullOrEmpty(request.phoneNumber) && phoneNumberValidator.test(request.phoneNumber);
        Boolean isEmailValid = !isNullOrEmpty(request.profileName) && emailValidator.test(request.email);

        CheckForInvalidInfo(request.profileName, isProfileNameValid, PROFILE_NAME);
        CheckForInvalidInfo(request.password, isPasswordValid, PASSWORD);
        CheckForInvalidInfo(request.phoneNumber, isPhoneNumberValid, PHONE_NUMBER);
        CheckForInvalidInfo(request.email, isEmailValid, EMAIL);

        CheckForRequiredFields(request.profileName, appUser.getProfileName(), PROFILE_NAME);
        CheckForRequiredFields(request.profileName, appUser.getProfileName(), PASSWORD);

        if(isProfileNameValid){
            appUser.setProfileName(request.profileName);
        }

        if(isPasswordValid){
            String encodedPassword = bCryptPasswordEncoder.encode(request.password);
            appUser.setPassword(encodedPassword);
        }

        if(isPhoneNumberValid){
            appUser.setPhoneNumber(request.phoneNumber);
        }

        if(isEmailValid){
            appUser.setEmail(request.email);
        }

        appUser.setProfileCreated(true);

        appUserRepository.save(appUser);

        return "Updated profile: " + appUser.getUsername();
    }

    private void CheckForInvalidInfo(String info, Boolean isValid, String name){
        if(!isNullOrEmpty(info) && !isValid){
            throw new IllegalStateException(name + " is not valid");
        }
    }

    private void CheckForRequiredFields(String info, String storedInfo, String name){
        if(isNullOrEmpty(info) && isNullOrEmpty(storedInfo)){
            throw new IllegalStateException(name + "  must have a value");
        }
    }

    private boolean isNullOrEmpty(String newValue){
        return newValue == null || newValue.isEmpty();
    }
}
