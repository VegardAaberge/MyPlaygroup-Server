package com.myplaygroup.server.user.service;

import com.myplaygroup.server.user.model.AppToken;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppTokenRepository;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.request.VerifyResetPasswordRequest;
import com.myplaygroup.server.user.response.SendResetPasswordResponse;
import com.myplaygroup.server.shared.data.SimpleResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResetPasswordService {

    AppTokenRepository appTokenRepository;
    AppUserRepository appUserRepository;

    public SendResetPasswordResponse sendResetPasswordRequest(String email) {

        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Email not found"));

        String token = UUID.randomUUID().toString();

        Double codeFloat = (Math.random() * (99999 - 11111)) + 11111;
        Long code = Math.round(codeFloat);

        AppToken appToken = new AppToken(
                token,
                code.toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        appTokenRepository.save(appToken);

        // TODO send email

        return new SendResetPasswordResponse(token);
    }

    public SimpleResponse verifyResetPasswordRequest(VerifyResetPasswordRequest request) {
        AppToken appToken = appTokenRepository
                .findByToken(request.token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if(appToken.getConfirmedAt() != null){
            throw new IllegalStateException("reset token already confirmed");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        if(appToken.getExpiresAt().isBefore(currentTime)){
            throw new IllegalStateException("token expired");
        }

        if(!appToken.getCode().equals(request.code)){
            throw new IllegalStateException("reset code is incorrect");
        }

        appToken.setConfirmedAt(currentTime);

        appTokenRepository.save(appToken);

        // TODO Reset Password form

        return new SimpleResponse("Reset password successful");
    }
}
