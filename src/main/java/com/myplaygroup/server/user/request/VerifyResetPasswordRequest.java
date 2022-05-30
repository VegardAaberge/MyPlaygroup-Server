package com.myplaygroup.server.user.request;

import javax.validation.constraints.NotBlank;

import static com.myplaygroup.server.shared.utils.Constants.CODE_VALIDATION_MSG;
import static com.myplaygroup.server.shared.utils.Constants.TOKEN_VALIDATION_MSG;

public class VerifyResetPasswordRequest {

    @NotBlank(message = TOKEN_VALIDATION_MSG)
    public String token;

    @NotBlank(message = CODE_VALIDATION_MSG)
    public String code;
}
