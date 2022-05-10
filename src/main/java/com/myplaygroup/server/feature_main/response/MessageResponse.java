package com.myplaygroup.server.feature_main.response;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageResponse {
     Long getId();

     String getMessage();

     LocalDateTime getCreated();

     LocalDateTime getRead();
}