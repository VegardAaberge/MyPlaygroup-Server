package com.myplaygroup.server.schedule.response;

public interface MonthlyPlanItem {

    Long getId();

    Boolean getPaid();

    String getPlanName();

    Long getPlanPrice();

    String getKidName();
}