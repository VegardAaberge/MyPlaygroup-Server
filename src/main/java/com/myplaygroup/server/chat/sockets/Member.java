package com.myplaygroup.server.chat.sockets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
@AllArgsConstructor
public class Member {
    private String username;
    private String sessionId;
    private WebSocketSession socket;
}
