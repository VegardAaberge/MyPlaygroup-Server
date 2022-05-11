package com.myplaygroup.server.feature_login.request;

import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotNull
    public String username;

    @NotNull
    public String password;
}
