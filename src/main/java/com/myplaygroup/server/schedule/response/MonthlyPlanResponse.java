package com.myplaygroup.server.schedule.response;

public interface MonthlyPlanResponse {

    Long getId();

    Boolean getPaid();

    String getPlanName();

    Long getPlanPrice();

    String getKidName();
}