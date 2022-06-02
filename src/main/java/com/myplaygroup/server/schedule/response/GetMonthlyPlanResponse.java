package com.myplaygroup.server.schedule.response;

import com.myplaygroup.server.schedule.model.DailyClass;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetMonthlyPlanResponse {

    public String username;

    public Long userCredit;

    public List<MonthlyPlanResponse> monthlyPlans;

    public List<DailyClass> dailyClasses;
}