package com.myplaygroup.server.user.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.myplaygroup.server.other.Constants.*;

public class RegistrationRequest {

    @Pattern(regexp=USERNAME_VALIDATION_REGEX, message=USERNAME_VALIDATION_MSG)
    public String username;

    @NotNull(message = PASSWORD_VALIDATION_MSG)
    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_VALIDATION_MSG)
    public String password;
}