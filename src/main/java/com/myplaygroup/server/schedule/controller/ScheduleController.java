package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.requests.CreateClassesRequest;
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
@RequestMapping(path = "api/v1/schedule")
@AllArgsConstructor
public class ScheduleController {

    AuthorizationService authorizationService;
    ScheduleService scheduleService;

    @GetMapping(path = "/plans")
    public List<MonthlyPlan> getUsersMonthlyPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return scheduleService.getUsersMonthlyPlans(username);
    }

    @PostMapping(path = "/create/classes")
    public List<DailyClass> createDailyClasses(
            @RequestBody @Valid CreateClassesRequest createClassesRequest,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return scheduleService.createDailyClasses(createClassesRequest);
    }

    @PostMapping(path = "/add/plan")
    public MonthlyPlan addMonthlyPlan(
            @RequestBody @Valid MonthlyPlanRequest monthlyPlanRequest,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return scheduleService.addMonthlyPlan(monthlyPlanRequest);
    }
}
