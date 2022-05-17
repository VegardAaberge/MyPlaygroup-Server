package com.myplaygroup.server.feature_main.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageResponse {
     Long getId();

     String getMessage();

     @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
     LocalDateTime getCreated();

     String getCreatedBy();

     String getProfileName();
}