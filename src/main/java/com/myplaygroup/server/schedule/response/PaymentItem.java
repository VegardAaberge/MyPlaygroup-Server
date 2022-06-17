package com.myplaygroup.server.schedule.response;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class PaymentItem {

    public Long id;

    public String clientId;

    public String username;

    public LocalDate date;

    public Long amount;

    public Boolean cancelled;
}