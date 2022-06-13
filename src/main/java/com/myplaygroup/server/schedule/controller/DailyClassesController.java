package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.requests.DailyClassRequest;
import com.myplaygroup.server.schedule.response.DailyClassResponse;
import com.myplaygroup.server.schedule.service.DailyClassesService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/classes")
@AllArgsConstructor
public class DailyClassesController {

    AuthorizationService authorizationService;
    DailyClassesService dailyClassesService;

    @GetMapping
    public List<DailyClassResponse> getAllClasses(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());

        return dailyClassesService.getAllClasses();
    }

    @PostMapping
    public List<DailyClassResponse> uploadDailyClasses(
            @RequestBody @Valid List<DailyClassRequest> dailyClasses,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return dailyClassesService.uploadDailyClasses(dailyClasses);
    }
}
