package com.myplaygroup.server.user.controller;

import com.myplaygroup.server.user.request.SendResetPasswordRequest;
import com.myplaygroup.server.user.request.VerifyResetPasswordRequest;
import com.myplaygroup.server.user.response.SendResetPasswordResponse;
import com.myplaygroup.server.user.service.ResetPasswordService;
import com.myplaygroup.server.other.SimpleResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/reset-password")
@AllArgsConstructor
public class ResetPasswordController {

    ResetPasswordService resetPasswordService;

    @PostMapping(path = "send")
    public SendResetPasswordResponse resetPassword(@RequestBody @Valid SendResetPasswordRequest request){
        log.info("api/v1/reset-password/send");
        return resetPasswordService.sendResetPasswordRequest(request.email);
    }

    @PostMapping(path = "verify")
    public SimpleResponse resetPassword(@RequestBody @Valid VerifyResetPasswordRequest request){
        log.info("api/v1/reset-password/verify");
        return resetPasswordService.verifyResetPasswordRequest(request);
    }
}
