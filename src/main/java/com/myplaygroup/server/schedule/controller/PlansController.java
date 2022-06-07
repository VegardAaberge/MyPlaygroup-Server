package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.service.ScheduleService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/plans")
@AllArgsConstructor
public class PlansController {

    AuthorizationService authorizationService;
    ScheduleService scheduleService;

    @GetMapping
    public List<MonthlyPlan> getMonthlyPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        return scheduleService.getMonthlyPlans();
    }

    @PostMapping
    public MonthlyPlan addMonthlyPlan(
            @RequestBody @Valid MonthlyPlanRequest monthlyPlanRequest,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return scheduleService.addMonthlyPlan(monthlyPlanRequest);
    }
}
