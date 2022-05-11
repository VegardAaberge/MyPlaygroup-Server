package com.myplaygroup.server.feature_login.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.myplaygroup.server.util.Constants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = PROFILE_NAME_VALIDATION_MSG)
    public String profileName;

    @NotNull(message = PASSWORD_VALIDATION_MSG)
    @Pattern(regexp = PASSWORD_VALIDATION_REGEX, message = PASSWORD_VALIDATION_MSG)
    public String password;

    @Pattern(regexp = PHONE_NUMBER_VALIDATION_REGEX, message = PHONE_NUMBER_VALIDATION_MSG)
    public String phoneNumber;

    @Email(message = EMAIL_VALIDATION_MSG)
    public String email;
}
