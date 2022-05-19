package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    // UsernamePasswordAuthenticationFilter provides the api/v1/login authentication route

    @GetMapping(path = "refresh_token")
    public Map<String, Object> refreshToken(HttpServletRequest request){
        log.info(request.getServletPath());
        return loginService.refreshTokens(request);
    }
}
