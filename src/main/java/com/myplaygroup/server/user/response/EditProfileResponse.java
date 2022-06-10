package com.myplaygroup.server.user.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditProfileResponse {
    private String username;
    private String profileName;
    private String phoneNumber;
}
