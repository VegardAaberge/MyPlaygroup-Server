package com.myplaygroup.server.schedule.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.myplaygroup.server.shared.utils.Constants.DATE_NAME_VALIDATION_MSG;

public class MonthlyPlanRequest {

    @NotNull
    @Size(min = 1)
    public List<DayOfWeek> daysOfWeek;

    @NotNull
    public String username;

    @NotNull
    public String kidName;

    @NotNull
    public Month month;

    @NotNull
    public String standardPlan;
}
