package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.model.DailyClass;
import com.myplaygroup.server.schedule.requests.CreateClassesRequest;
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
    public List<DailyClass> getAllClasses(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());

        return dailyClassesService.getAllClasses();
    }

    @PostMapping
    public List<DailyClass> createDailyClasses(
            @RequestBody @Valid CreateClassesRequest createClassesRequest,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return dailyClassesService.createDailyClasses(createClassesRequest);
    }
}
