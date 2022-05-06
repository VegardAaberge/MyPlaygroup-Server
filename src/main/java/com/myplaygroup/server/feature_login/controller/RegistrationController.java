package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.RegistrationRequest;
import com.myplaygroup.server.feature_login.request.UpdateProfileRequest;
import com.myplaygroup.server.feature_login.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Registration;

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
    public String updateProfile(
            @PathVariable("username") String username,
            @RequestBody UpdateProfileRequest request
    ){
        return registrationService.updateProfile(username, request);
    }
}
