package com.myplaygroup.server.schedule.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.Payment;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.repository.DailyClassRepository;
import com.myplaygroup.server.schedule.repository.MonthlyPlanRepository;
import com.myplaygroup.server.schedule.repository.PaymentsRepository;
import com.myplaygroup.server.schedule.repository.StandardPlanRepository;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.response.UserScheduleResponse;
import com.myplaygroup.server.schedule.response.MonthlyPlanItem;
import com.myplaygroup.server.schedule.response.PaymentItem;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final DailyClassRepository dailyClassRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final PaymentsRepository paymentsRepository;
    private final StandardPlanRepository standardPlanRepository;
    private final AppUserService userService;

    public UserScheduleResponse getUserSchedule(String username) {
        AppUser appUser = userService.loadUserByUsername(username);

        List<Payment> payments = paymentsRepository.findByUsername(username);
        List<MonthlyPlan> monthlyPlans = monthlyPlanRepository.findByUsername(username);

        List<DailyClass> dailyClasses = new ArrayList<>();
        monthlyPlans.forEach(monthlyPlan -> {
            monthlyPlan.getClasses().forEach(dailyClass -> {
                if(!dailyClasses.stream().anyMatch(x -> x.getId().equals(dailyClass.getId()))){
                    dailyClasses.add(dailyClass);
                }
            });
        });

        List<PaymentItem> paymentItems = getPaymentItems(payments);
        List<MonthlyPlanItem> monthlyPlanItem = getMonthlyPlanItems(monthlyPlans);

        return new UserScheduleResponse(
                appUser.getUsername(),
                paymentItems,
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

    public List<MonthlyPlanItem> addMonthlyPlan(List<MonthlyPlanRequest> request) {

        request.forEach(item -> {
            if(item.id == -1){
                addNewMonthlyPlan(item);
            }else {
                modifyMonthlyPlan(item);
            }
        });

        return getMonthlyPlans();
    }

    private void modifyMonthlyPlan(MonthlyPlanRequest item){
        MonthlyPlan monthlyPlan = monthlyPlanRepository.findById(item.id)
                .orElseThrow(() -> new NotFoundException("Monthly plan not found"));

        monthlyPlan.setKidName(item.kidName);
        monthlyPlan.setPlanPrice(item.planPrice);
        monthlyPlan.setCancelled(item.cancelled);

        if(item.changeDays){
            StandardPlan standardPlan = standardPlanRepository.findByName(item.planName)
                    .orElseThrow(() -> new NotFoundException("Plan not found"));

            List<DailyClass> dailyClasses = getMonthlyPlanDays(item, standardPlan);

            monthlyPlan.setPlan(standardPlan);
            monthlyPlan.setDaysOfWeek(item.daysOfWeek);
            monthlyPlan.setStartDate(item.startDate);
            monthlyPlan.setEndDate(item.endDate);
            monthlyPlan.setClasses(dailyClasses);
        }

        monthlyPlanRepository.save(monthlyPlan);
    }

    private void addNewMonthlyPlan(MonthlyPlanRequest item){

        AppUser appUser = userService.loadUserByUsername(item.username);

        StandardPlan standardPlan = standardPlanRepository.findByName(item.planName)
                .orElseThrow(() -> new NotFoundException("Plan not found"));

        List<DailyClass> dailyClasses = getMonthlyPlanDays(item, standardPlan);

        MonthlyPlan monthlyPlan = new MonthlyPlan(
                item.clientId,
                item.kidName,
                appUser,
                item.startDate,
                item.endDate,
                standardPlan,
                item.planPrice,
                dailyClasses,
                item.daysOfWeek
        );
        monthlyPlanRepository.save(monthlyPlan);
    }

    private List<DailyClass> getMonthlyPlanDays(
            MonthlyPlanRequest item,
            StandardPlan standardPlan
    ){
        List<Integer> dayOfWeeks = item.daysOfWeek.stream()
                .map(Enum::ordinal)
                .collect(Collectors.toList());

        List<DailyClass> dailyClasses = dailyClassRepository.findByDatesAndClassType(
                dayOfWeeks,
                standardPlan.getType().ordinal(),
                item.startDate,
                item.endDate
        );
        if(dailyClasses.isEmpty()){
            throw new NotFoundException("No classes found");
        }

        return dailyClasses;
    }

    private List<MonthlyPlanItem> getMonthlyPlanItems(List<MonthlyPlan> monthlyPlans){
        return monthlyPlans.stream().map(item -> new MonthlyPlanItem(
                item.getId(),
                item.getClientId(),
                item.getAppUser().getUsername(),
                item.getKidName(),
                item.getStartDate(),
                item.getEndDate(),
                item.getPlan().getName(),
                item.getDaysOfWeek(),
                item.getPlanPrice(),
                item.getCancelled(),
                false
        )).collect(Collectors.toList());
    }

    private List<PaymentItem> getPaymentItems(List<Payment> payments){
        return payments.stream().map(item -> new PaymentItem(
                item.getId(),
                item.getClientId(),
                item.getAppUser().getUsername(),
                item.getDate(),
                item.getAmount(),
                item.getCancelled()
        )).collect(Collectors.toList());
    }
}
