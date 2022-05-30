package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.model.MonthlyPlan;
import com.myplaygroup.server.schedule.model.StandardPlan;
import com.myplaygroup.server.schedule.service.ScheduleService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

        return scheduleService.getUsersMonthlyPlans();
    }
}
