package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.EditProfileRequest;
import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.response.EditProfileResponse;
import com.myplaygroup.server.feature_login.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody @Valid RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }

    @PostMapping(path = "profile/create/{username}")
    public EditProfileResponse updateProfile(
            @PathVariable("username") String username,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        return registrationService.updateProfile(username, request);
    }

    @PostMapping(path = "profile/edit/{username}")
    public EditProfileResponse editProfile(
            @PathVariable("username") String username,
            @RequestBody @Valid EditProfileRequest request
    ) {
        return registrationService.editProfile(username, request);
    }
}
