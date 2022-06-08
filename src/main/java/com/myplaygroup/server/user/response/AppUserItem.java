package com.myplaygroup.server.user.response;

public interface AppUserItem {

    Long getId();

    String getUsername();

    String getProfileName();

    String getPhoneNumber();

    String getEmail();

    Long getUserCredit();

    Boolean getLocked();

    Boolean getProfileCreated();
}
