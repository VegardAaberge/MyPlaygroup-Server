package com.myplaygroup.server.schedule.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface StandardPlanItem {

     Long getId();

     String getName();

     Integer getPrice();
}