package com.myplaygroup.server.chat.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static com.myplaygroup.server.other.Constants.MESSAGE_VALIDATION_MSG;
import static com.myplaygroup.server.other.Constants.RECEIVERS_VALIDATION_MSG;

public class MessageRequest {

    @NotBlank(message = MESSAGE_VALIDATION_MSG)
    public String message;

    @Size(min = 1, message = RECEIVERS_VALIDATION_MSG)
    public List<String> receivers;
}