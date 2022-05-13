package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.LoginRequest;
import com.myplaygroup.server.feature_login.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @GetMapping(path = "refresh_token")
    public Map<String, String> refreshTokens(HttpServletRequest request){
        return loginService.refreshTokens(request);
    }
}
