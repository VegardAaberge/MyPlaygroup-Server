package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.response.MonthlyPlanItem;
import com.myplaygroup.server.schedule.response.StandardPlanItem;
import com.myplaygroup.server.schedule.service.ScheduleService;
import com.myplaygroup.server.schedule.service.StandardPlanService;
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
    StandardPlanService standardPlanService;

    @GetMapping
    public List<MonthlyPlanItem> getMonthlyPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        return scheduleService.getMonthlyPlans();
    }

    @GetMapping(path = "/standard")
    public List<StandardPlanItem> getStandardPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        return standardPlanService.getStandardPlans();
    }

    @PostMapping
    public List<MonthlyPlanItem> addMonthlyPlan(
            @RequestBody @Valid List<MonthlyPlanRequest> monthlyPlanRequest,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return scheduleService.addMonthlyPlan(monthlyPlanRequest);
    }
}
