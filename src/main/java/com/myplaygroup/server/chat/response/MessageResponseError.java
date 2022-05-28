package com.myplaygroup.server.chat.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageResponseError {
    public Long id = -1L;
    public String clientId;
    public String message;
    public String profileName;
    public String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime created = LocalDateTime.now();

    public MessageResponseError(String clientId, String message, String createdBy, String profileName) {
        this.clientId = clientId;
        this.message = message;
        this.createdBy = createdBy;
        this.profileName = profileName;
    }
}
