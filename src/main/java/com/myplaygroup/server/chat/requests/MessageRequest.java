package com.myplaygroup.server.chat.requests;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.myplaygroup.server.shared.utils.Constants.*;

public class MessageRequest {

    @NotNull(message = READ_BY_VALIDATION_MSG)
    public Long id;

    @NotBlank(message = CLIENT_ID_VALIDATION_MSG)
    public String clientId;

    @Pattern(regexp=USERNAME_VALIDATION_REGEX, message=USERNAME_VALIDATION_MSG)
    public String createdBy;

    @NotBlank(message = MESSAGE_VALIDATION_MSG)
    public String message;

    @Size(min = 1, message = RECEIVERS_VALIDATION_MSG)
    public List<String> receivers;

    @NotNull(message = READ_BY_VALIDATION_MSG)
    public List<String> readBy;
}
