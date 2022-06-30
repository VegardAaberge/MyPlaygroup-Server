package com.myplaygroup.server.user.request;

import com.myplaygroup.server.user.model.EditProfileType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.myplaygroup.server.shared.utils.Constants.*;

public class EditProfileRequest {
    public String profileName;

    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_VALIDATION_MSG)
    public String password;

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_VALIDATION_MSG)
    public String phoneNumber;

    @NotNull
    public EditProfileType editProfileType;
}
