package com.myplaygroup.server.user.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.myplaygroup.server.shared.utils.Constants.*;

public class SendResetPasswordRequest {

    @NotBlank(message = EMAIL_VALIDATION_MSG)
    @Email(message = EMAIL_VALIDATION_MSG)
    public String email;
}
