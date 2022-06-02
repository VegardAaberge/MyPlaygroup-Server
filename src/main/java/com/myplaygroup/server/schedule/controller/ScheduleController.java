package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.requests.CreateClassesRequest;
import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.response.GetMonthlyPlanResponse;
import com.myplaygroup.server.schedule.response.MonthlyPlanResponse;
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

    @GetMapping
    public GetMonthlyPlanResponse getUsersMonthlyPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return scheduleService.getUsersMonthlyPlans(username);
    }
}
