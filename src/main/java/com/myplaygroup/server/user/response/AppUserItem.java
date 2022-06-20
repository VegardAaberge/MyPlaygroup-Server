package com.myplaygroup.server.user.response;

public interface AppUserItem {

    Long getId();

    String getClientId();

    String getUsername();

    String getProfileName();

    String getPhoneNumber();

    Boolean getLocked();

    Boolean getProfileCreated();
}
