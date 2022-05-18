package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.EditProfileRequest;
import com.myplaygroup.server.user.request.RegistrationRequest;
import com.myplaygroup.server.user.request.UpdateProfileRequest;
import com.myplaygroup.server.user.response.EditProfileResponse;
import com.myplaygroup.server.user.service.RegistrationService;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public SimpleResponse register(@RequestBody @Valid RegistrationRequest registrationRequest){
        return registrationService.register(registrationRequest);
    }
}
