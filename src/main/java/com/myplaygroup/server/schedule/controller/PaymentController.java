package com.myplaygroup.server.schedule.controller;

import com.myplaygroup.server.schedule.requests.MonthlyPlanRequest;
import com.myplaygroup.server.schedule.requests.PaymentRequest;
import com.myplaygroup.server.schedule.response.MonthlyPlanItem;
import com.myplaygroup.server.schedule.response.PaymentItem;
import com.myplaygroup.server.schedule.response.StandardPlanItem;
import com.myplaygroup.server.schedule.service.PaymentService;
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
@RequestMapping(path = "api/v1/payments")
@AllArgsConstructor
public class PaymentController {

    AuthorizationService authorizationService;
    PaymentService paymentService;

    @GetMapping
    public List<PaymentItem> getPayments(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        return paymentService.getPayments();
    }

    @PostMapping
    public List<PaymentItem> addMPayments(
            @RequestBody @Valid List<PaymentRequest> paymentRequests,
            HttpServletRequest servletRequest
    ){
        log.info(servletRequest.getServletPath());
        return paymentService.addPayments(paymentRequests);
    }
}
