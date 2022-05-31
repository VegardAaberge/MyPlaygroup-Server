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
import com.myplaygroup.server.schedule.response.MonthlyPlanResponse;
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

    private final DailyClassRepository dailyClassRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final StandardPlanRepository standardPlanRepository;
    private final AppUserService userService;

    public List<MonthlyPlanResponse> getUsersMonthlyPlans(String username) {
        return monthlyPlanRepository.findByUsername(username);
    }

    public List<DailyClass> createDailyClasses(CreateClassesRequest createClassesRequest) {

        List<DailyClass> dailyClasses = new ArrayList<>();

        createClassesRequest.classRequests.forEach(request -> {
            Optional<DailyClass> dailyClass = dailyClassRepository.findByDateAndClassType(
                    request.date,
                    request.classType.ordinal()
            );
            if(dailyClass.isEmpty()){
                dailyClasses.add(
                        new DailyClass(
                                request.date,
                                request.startTime,
                                request.endTime,
                                request.classType
                        )
                );
            }
        });

        if(!dailyClasses.isEmpty()){
            dailyClassRepository.saveAll(dailyClasses);
        }

        return dailyClasses;
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
                appUser,
                standardPlan,
                dailyClasses
        );
        monthlyPlanRepository.save(monthlyPlan);

        return monthlyPlan;
    }
}
