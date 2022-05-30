package com.myplaygroup.server.schedule.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myplaygroup.server.schedule.model.DailyClassType;

import java.time.LocalDate;
import java.time.LocalTime;

public class DailyClassRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    public LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    public LocalTime endTime;

    public DailyClassType classType;
}