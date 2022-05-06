package com.myplaygroup.server.feature_login.request;

import org.springframework.web.bind.annotation.RequestParam;

public class UpdateProfileRequest {
    public String profileName;
    public String password;
    public String phoneNumber;
    public String email;
}
