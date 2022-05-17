package com.myplaygroup.server.feature_login.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileResponse {
    private String username;
    private String profileName;
    private String phoneNumber;
    private String email;
}
