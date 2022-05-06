package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.ResetPasswordRequest;
import com.myplaygroup.server.feature_login.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/reset-password")
@AllArgsConstructor
public class ResetPasswordController {

    ResetPasswordService resetPasswordService;

    @GetMapping
    public String resetPassword(@RequestParam String email){
        return resetPasswordService.requestResetPassword(email);
    }

    @PostMapping
    public String resetPassword(@RequestBody ResetPasswordRequest request){
        return resetPasswordService.resetPassword(request);
    }
}
