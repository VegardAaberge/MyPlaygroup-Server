package com.myplaygroup.server.schedule.response;

import com.myplaygroup.server.schedule.model.DailyClass;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserScheduleResponse {

    public String username;

    public List<PaymentItem> payments;

    public List<MonthlyPlanItem> monthlyPlans;

    public List<DailyClass> dailyClasses;
}
