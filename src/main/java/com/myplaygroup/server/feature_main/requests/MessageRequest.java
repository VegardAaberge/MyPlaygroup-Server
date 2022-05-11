package com.myplaygroup.server.feature_main.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    public String message;
    public List<String> receivers;
}
