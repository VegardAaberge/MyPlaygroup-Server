package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.LoginRequest;
import com.myplaygroup.server.feature_login.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @PostMapping
    public String login(@RequestBody LoginRequest loginRequest){
        return loginService.authenticate(loginRequest);
    }
}