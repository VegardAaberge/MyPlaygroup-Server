package com.myplaygroup.server.schedule.response;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.CLIENT_ID_VALIDATION_MSG;

@AllArgsConstructor
public class MonthlyPlanItem {

    public Long id;

    public String clientId;

    public String username;

    public String kidName;

    public LocalDate startDate;

    public LocalDate endDate;

    public String planName;

    public List<DayOfWeek> daysOfWeek;

    public Long planPrice;

    public Boolean cancelled;

    public Boolean changeDays;
}