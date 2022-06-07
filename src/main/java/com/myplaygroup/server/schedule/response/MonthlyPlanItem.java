package com.myplaygroup.server.schedule.response;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@AllArgsConstructor
public class MonthlyPlanItem {

    public Long id;

    public Boolean paid;

    public String planName;

    public List<DayOfWeek> daysOfWeek;

    public Integer planPrice;

    public String kidName;
}