package com.myplaygroup.server.feature_login.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendResetPasswordResponse {
    private String token;
}
