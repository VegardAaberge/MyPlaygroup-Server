package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.SendResetPasswordRequest;
import com.myplaygroup.server.user.request.VerifyResetPasswordRequest;
import com.myplaygroup.server.user.response.SendResetPasswordResponse;
import com.myplaygroup.server.user.service.ResetPasswordService;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
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
