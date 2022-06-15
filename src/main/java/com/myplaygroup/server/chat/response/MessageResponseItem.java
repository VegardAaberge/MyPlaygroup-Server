package com.myplaygroup.server.chat.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myplaygroup.server.user.model.AppUser;
import lombok.AllArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static com.myplaygroup.server.shared.utils.Constants.*;

@AllArgsConstructor
public class MessageResponseItem {
    public Long id;

    public String clientId;

    public String message;

    public String createdBy;

    public List<String> receivers;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime created;
}
