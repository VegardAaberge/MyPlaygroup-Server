package com.myplaygroup.server.user.service;

import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.repository.AppTokenRepository;
import com.myplaygroup.server.user.request.EditProfileRequest;
import com.myplaygroup.server.user.request.RegistrationRequest;
import com.myplaygroup.server.user.request.UpdateProfileRequest;
import com.myplaygroup.server.user.response.EditProfileResponse;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.myplaygroup.server.user.model.AppUser.UserRole.USER;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserRepository appUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SimpleResponse register(RegistrationRequest request) {
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

        return new SimpleResponse("Registered user: " + request.username);
    }
}
