package com.myplaygroup.server.feature_login.response;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileResponse {
    private String username;
    private String profileName;
    private String phoneNumber;
    private String email;
}
