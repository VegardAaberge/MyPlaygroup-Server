package com.myplaygroup.server.feature_login.controller;

import com.myplaygroup.server.feature_login.request.SendResetPasswordRequest;
import com.myplaygroup.server.feature_login.request.VerifyResetPasswordRequest;
import com.myplaygroup.server.feature_login.response.SendResetPasswordResponse;
import com.myplaygroup.server.feature_login.service.ResetPasswordService;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/reset-password")
@AllArgsConstructor
public class ResetPasswordController {

    ResetPasswordService resetPasswordService;

    @PostMapping(path = "send")
    public SendResetPasswordResponse resetPassword(@RequestBody @Valid SendResetPasswordRequest request){
        return resetPasswordService.sendResetPasswordRequest(request.email);
    }

    @PostMapping(path = "verify")
    public SimpleResponse resetPassword(@RequestBody @Valid VerifyResetPasswordRequest request){
        return resetPasswordService.verifyResetPasswordRequest(request);
    }
}
