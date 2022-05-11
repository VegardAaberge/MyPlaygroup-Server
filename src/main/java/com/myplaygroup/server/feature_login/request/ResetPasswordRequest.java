package com.myplaygroup.server.feature_login.request;

import javax.validation.constraints.NotBlank;

import static com.myplaygroup.server.util.Constants.CODE_VALIDATION_MSG;
import static com.myplaygroup.server.util.Constants.TOKEN_VALIDATION_MSG;

public class ResetPasswordRequest {

    @NotBlank(message = TOKEN_VALIDATION_MSG)
    public String token;

    @NotBlank(message = CODE_VALIDATION_MSG)
    public String code;
}
