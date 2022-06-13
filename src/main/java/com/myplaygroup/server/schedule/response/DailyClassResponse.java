package com.myplaygroup.server.schedule.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.DailyClassType;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DailyClassResponse {

    public Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime endTime;

    public DailyClassType classType;

    public DayOfWeek dayOfWeek;

    public List<String> kids;

    public Boolean cancelled;

    public DailyClassResponse(DailyClass dailyClass, List<String> kids) {
        this.id = dailyClass.getId();
        this.date = dailyClass.getDate();
        this.startTime = dailyClass.getStartTime();
        this.endTime = dailyClass.getEndTime();
        this.classType = dailyClass.getClassType();
        this.dayOfWeek = dailyClass.getDayOfWeek();
        this.cancelled = dailyClass.getCancelled();
        this.kids =  kids;
    }
}
