package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.EditProfileRequest;
import com.myplaygroup.server.user.request.RegistrationRequest;
import com.myplaygroup.server.user.request.UpdateProfileRequest;
import com.myplaygroup.server.user.response.EditProfileResponse;
import com.myplaygroup.server.user.service.RegistrationService;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public SimpleResponse register(@RequestBody @Valid RegistrationRequest registrationRequest){
        log.info("api/v1/registration");
        return registrationService.register(registrationRequest);
    }
}
