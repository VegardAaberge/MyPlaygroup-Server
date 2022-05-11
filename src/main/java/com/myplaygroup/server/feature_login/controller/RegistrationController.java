package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.response.UpdateProfileResponse;
import com.myplaygroup.server.feature_login.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Registration;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }

    @PostMapping(path = "update/{username}")
    public UpdateProfileResponse updateProfile(
            @PathVariable("username") String username,
            @RequestBody @Valid UpdateProfileRequest request
    ) {
        return registrationService.updateProfile(username, request);
    }
}
