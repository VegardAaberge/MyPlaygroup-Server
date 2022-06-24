package com.myplaygroup.server.chat.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageResponse {
     Long getId();

     String getClientId();

     String getMessage();

     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     LocalDateTime getCreated();

     String getCreatedBy();

     String getProfileName();

     List<String> getReadBy();

     List<String> getReceivers();
}