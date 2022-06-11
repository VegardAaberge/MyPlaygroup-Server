package com.myplaygroup.server.user.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.*;

public class AppUserRequest {

    @NotNull
    public Long id;

    @NotBlank(message = CLIENT_ID_VALIDATION_MSG)
    public String clientId = UUID.randomUUID().toString();

    @Pattern(regexp=USERNAME_VALIDATION_REGEX, message=USERNAME_VALIDATION_MSG)
    public String username;

    public String profileName;

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_VALIDATION_MSG)
    public String phoneNumber;

    @NotNull
    public Long userCredit;

    @NotNull
    public Boolean locked;

    @NotNull
    public Boolean profileCreated;

    @NotNull
    public Boolean resetPassword;
}