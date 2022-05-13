package com.myplaygroup.server.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {
    public String username;
    public String token;
}
