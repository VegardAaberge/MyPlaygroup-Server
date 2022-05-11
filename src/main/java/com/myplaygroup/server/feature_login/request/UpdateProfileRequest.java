package com.myplaygroup.server.feature_login.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "profileName shouldn't be blank")
    public String profileName;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Password must be at least 8 letters, contain one number, lowercase and one uppercase letter")
    public String password;

    @Pattern(regexp = "^\\d{11}$", message = "Phone number need to have 11 digits")
    public String phoneNumber;

    @Email(message = "Not a valid email")
    public String email;
}
