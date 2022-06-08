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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final DailyClassRepository dailyClassRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final StandardPlanRepository standardPlanRepository;
    private final AppUserService userService;

    public MonthlyPlansResponse getUsersMonthlyPlans(String username) {
        AppUser appUser = userService.loadUserByUsername(username);
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository.findByUsername(username);
        List<DailyClass> dailyClasses = dailyClassRepository.findByUsername(username);

        List<MonthlyPlanItem> monthlyPlanItem = getMonthlyPlanItems(monthlyPlans);

        return new MonthlyPlansResponse(
                appUser.getUsername(),
                appUser.getUserCredit(),
                monthlyPlanItem,
                dailyClasses
        );
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    public List<MonthlyPlanItem> getMonthlyPlans() {
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository.findAll(Sort.by("id"));

        List<MonthlyPlanItem> monthlyPlanItem = getMonthlyPlanItems(monthlyPlans);

        return monthlyPlanItem;
    }

    public MonthlyPlan addMonthlyPlan(MonthlyPlanRequest request) {

        AppUser appUser = userService.loadUserByUsername(request.username);

        StandardPlan standardPlan = standardPlanRepository.findByName(request.standardPlan)
                .orElseThrow(() -> new NotFoundException("Plan not found"));


        List<Integer> dayOfWeeks = request.daysOfWeek.stream()
                .map(Enum::ordinal)
                .collect(Collectors.toList());

        List<DailyClass> dailyClasses = dailyClassRepository.findByDatesAndClassType(
                dayOfWeeks,
                standardPlan.getType().ordinal()
        );
        if(dailyClasses.isEmpty()){
            throw new NotFoundException("No classes found");
        }

        MonthlyPlan monthlyPlan = new MonthlyPlan(
                request.kidName,
                appUser,
                standardPlan,
                dailyClasses,
                request.daysOfWeek
        );
        monthlyPlanRepository.save(monthlyPlan);

        return monthlyPlan;
    }

    private List<MonthlyPlanItem> getMonthlyPlanItems(List<MonthlyPlan> monthlyPlans){
        return monthlyPlans.stream().map(item -> new MonthlyPlanItem(
                item.getId(),
                item.getPaid(),
                item.getPlan().getName(),
                item.getDaysOfWeek(),
                item.getPlan().getPrice(),
                item.getKidName()
        )).collect(Collectors.toList());
    }
}
