package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.RegistrationRequest;
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

    @PutMapping(path = "{username}")
    public String updateProfile(
            @PathVariable("username") String username,
            @RequestParam String profileName,
            @RequestParam String password,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email
    ){
        return registrationService.updateProfile(username, profileName, password, phoneNumber, email);
    }
}
