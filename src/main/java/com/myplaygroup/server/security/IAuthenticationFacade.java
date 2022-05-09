package com.myplaygroup.server.security;

import com.myplaygroup.server.feature_login.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    String getUsername();
}