package com.myplaygroup.server.feature_login.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "profileName shouldn't be null")
    public String profileName;

    public String password;

    @Pattern(regexp = "^\\d{11}$", message = "invalid mobile number")
    public String phoneNumber;

    @Email(message = "Not a valid email")
    public String email;
}
