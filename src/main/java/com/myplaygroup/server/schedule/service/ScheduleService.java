package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.exception.BadRequestException;
import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.DailyClassType;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.repository.MonthlyPlanRepository;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.schedule.requests.CreateClassesRequest;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    DailyClassRepository dailyClassRepository;
    MonthlyPlanRepository monthlyPlanRepository;
    StandardPlanRepository standardPlanRepository;
    AppUserService userService;

    public List<MonthlyPlan> getUsersMonthlyPlans(String username) {
        return null; //monthlyPlanRepository.findByUsername(username);
    }

    public List<DailyClass> createDailyClasses(CreateClassesRequest createClassesRequest) {

        List<DailyClass> dailyClasses = new ArrayList<>();

        createClassesRequest.classRequests.forEach(dailyClassRequest -> {
            Optional<DailyClass> dailyClass = null; //dailyClassRepository.findByDateAndClassType(dailyClassRequest.date, dailyClassRequest.startTime);
            if(dailyClass.isEmpty()){
                dailyClasses.add(
                        new DailyClass(
                                dailyClassRequest.date,
                                dailyClassRequest.startTime,
                                dailyClassRequest.endTime,
                                dailyClassRequest.classType
                        )
                );
            }
        });

        if(!dailyClasses.isEmpty()){
            dailyClassRepository.saveAll(dailyClasses);
        }

        return dailyClasses;
    }

    public MonthlyPlan addMonthlyPlan(MonthlyPlanRequest monthlyPlanRequest) {

        AppUser appUser = userService.loadUserByUsername(monthlyPlanRequest.username);

        /*
        StandardPlan standardPlan = standardPlanRepository.findByName(monthlyPlanRequest.standardPlan)
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        List<DailyClass> dailyClasses = null; dailyClassRepository.findByDaysAndClassType(
                monthlyPlanRequest.days,
                standardPlan.getType()
        );

        MonthlyPlan monthlyPlan = new MonthlyPlan(
                appUser,
                standardPlan,
                dailyClasses
        );
        monthlyPlanRepository.save(monthlyPlan);
*/
        return new MonthlyPlan();
    }
}
