package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.response.MonthlyPlansResponse;
import com.myplaygroup.server.schedule.service.ScheduleService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/schedule")
@AllArgsConstructor
public class ScheduleController {

    AuthorizationService authorizationService;
    ScheduleService scheduleService;

    @GetMapping
    public MonthlyPlansResponse getUsersMonthlyPlans(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return scheduleService.getUsersMonthlyPlans(username);
    }
}
