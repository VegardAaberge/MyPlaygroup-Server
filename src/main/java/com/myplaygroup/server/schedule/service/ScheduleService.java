package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.repository.MonthlyPlanRepository;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.response.MonthlyPlansResponse;
import com.myplaygroup.server.schedule.response.MonthlyPlanItem;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final DailyClassRepository dailyClassRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final StandardPlanRepository standardPlanRepository;
    private final AppUserService userService;

    public MonthlyPlansResponse getUsersMonthlyPlans(String username) {
        AppUser appUser = userService.loadUserByUsername(username);
        List<MonthlyPlanItem> monthlyPlans = monthlyPlanRepository.findByUsername(username);
        List<DailyClass> dailyClasses = dailyClassRepository.findByUsername(username);

        return new MonthlyPlansResponse(
                appUser.getUsername(),
                appUser.getUserCredit(),
                monthlyPlans,
                dailyClasses
        );
    }

    public MonthlyPlan addMonthlyPlan(MonthlyPlanRequest request) {

        AppUser appUser = userService.loadUserByUsername(request.username);

        StandardPlan standardPlan = standardPlanRepository.findByName(request.standardPlan)
                .orElseThrow(() -> new NotFoundException("Plan not found"));


        List<DailyClass> dailyClasses = dailyClassRepository.findByDatesAndClassType(
                request.days,
                standardPlan.getType().ordinal()
        );
        if(dailyClasses.isEmpty()){
            throw new NotFoundException("No classes found");
        }

        MonthlyPlan monthlyPlan = new MonthlyPlan(
                request.kidName,
                appUser,
                standardPlan,
                dailyClasses
        );
        monthlyPlanRepository.save(monthlyPlan);

        return monthlyPlan;
    }
}
