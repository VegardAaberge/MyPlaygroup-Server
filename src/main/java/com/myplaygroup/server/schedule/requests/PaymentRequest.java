package com.myplaygroup.server.schedule.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.myplaygroup.server.shared.utils.Constants.CLIENT_ID_VALIDATION_MSG;

public class PaymentRequest {

    @NotNull
    public Long id;

    @NotNull(message = CLIENT_ID_VALIDATION_MSG)
    public String clientId = UUID.randomUUID().toString();

    @NotNull
    public String username;


    @NotNull
    public LocalDate date;

    @NotNull
    public Long amount;

    @NotNull
    public Boolean cancelled;
}
