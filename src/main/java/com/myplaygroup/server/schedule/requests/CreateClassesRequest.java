package com.myplaygroup.server.schedule.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

import static com.myplaygroup.server.shared.utils.Constants.CLASS_REQUESTS_VALIDATION_MSG;

public class CreateClassesRequest {

    @NotNull(message = CLASS_REQUESTS_VALIDATION_MSG)
    @Size(min = 1, message = CLASS_REQUESTS_VALIDATION_MSG)
    public ArrayList<DailyClassRequest> classRequests;
}
