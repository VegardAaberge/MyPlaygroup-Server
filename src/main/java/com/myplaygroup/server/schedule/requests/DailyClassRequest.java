package com.myplaygroup.server.schedule.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myplaygroup.server.schedule.model.DailyClassType;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class DailyClassRequest {

    @NotNull
    public Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime endTime;

    @NotNull
    public DailyClassType classType;

    @NotNull
    public DayOfWeek dayOfWeek;

    @NotNull
    public Boolean cancelled;
}