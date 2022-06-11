package com.myplaygroup.server.schedule.response;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@AllArgsConstructor
public class MonthlyPlanItem {

    public Long id;

    public String username;

    public String kidName;

    public LocalDate startDate;

    public LocalDate endDate;

    public String planName;

    public List<DayOfWeek> daysOfWeek;

    public Integer planPrice;
}