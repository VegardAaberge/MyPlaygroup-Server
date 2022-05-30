package com.myplaygroup.server.schedule.requests;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.myplaygroup.server.shared.utils.Constants.DATE_NAME_VALIDATION_MSG;

public class MonthlyPlanRequest {

    @NotNull(message = DATE_NAME_VALIDATION_MSG)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Size(min = 1)
    public List<LocalDate> days;

    @NotNull
    public String username;

    @NotNull
    public String standardPlan;
}
