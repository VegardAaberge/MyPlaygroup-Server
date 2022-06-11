package com.myplaygroup.server.schedule.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class MonthlyPlanRequest {

    @NotNull
    @Size(min = 1)
    public List<DayOfWeek> daysOfWeek;

    @NotNull
    public String username;

    @NotNull
    public String kidName;

    @NotNull
    public LocalDate startDate;

    public LocalDate endDate;

    @NotNull
    public String standardPlan;
}
