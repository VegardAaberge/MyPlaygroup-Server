package com.myplaygroup.server.schedule.response;

import com.myplaygroup.server.schedule.model.DailyClass;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MonthlyPlansResponse {

    public String username;

    public List<MonthlyPlanItem> monthlyPlans;

    public List<DailyClass> dailyClasses;
}
