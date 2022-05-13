package com.myplaygroup.server.security;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.security.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthorizationService {

    UserInfo getUserInfoFromRequest(HttpServletRequest request);

    void setAuthenticationToken(String token);

    Map<String, String> getAccessAndRefreshToken(AppUser user, String requestUrl);

    Map<String, String> getAccessTokenFromRefreshToken(String refresh_token, String requestUrl, AppUser user);
}
