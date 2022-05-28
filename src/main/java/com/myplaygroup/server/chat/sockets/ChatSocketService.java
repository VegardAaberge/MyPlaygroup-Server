package com.myplaygroup.server.chat.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.security.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatSocketService {

    private final ChatService chatService;
    private final AuthorizationService authorizationService;
    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        String username = getUsernameFromSession(session);

        ObjectReader reader = new ObjectMapper().findAndRegisterModules().reader();
        MessageRequest request = reader.readValue(message.getPayload(), MessageRequest.class);

        String messageJson = chatService.storeMessage(
                username,
                request.clientId,
                request.message,
                request.receivers
        );

        List<Member> receivingMembers = members
                .values().stream()
                .filter(m -> request.receivers.contains(m.getUsername()) || Objects.equals(m.getUsername(), username)).
                collect(Collectors.toList());

        receivingMembers.forEach(member -> {
            try {
                member.getSocket().sendMessage(new TextMessage(messageJson));
            } catch (IOException e) {
                throw new ServerErrorException("There was an error sending message from the socket");
            }
        });
    }

    public void connectNewMember(WebSocketSession session) {
        String username = getUsernameFromSession(session);

        if(members.containsKey(username)) {
            throw new IllegalStateException("There is already a member with that username in the room.");
        }
        members.put(username, new Member(
                username,
                session.getId(),
                session
        ));
    }

    public void disconnectMember(WebSocketSession session) {
        String username = getUsernameFromSession(session);

        log.info("Disconnect " + username);
        Member member = members.get(username);
        if(member != null && members.containsKey(username) && member.getSocket() != null){
            members.remove(username);
        }
    }

    private String getUsernameFromSession(WebSocketSession session){
        String authorizationHeader = session.getHandshakeHeaders().get("cookie").get(0);
        UserInfo userInfo = authorizationService.getUserInfoFromRequest(authorizationHeader);

        return userInfo.getUsername();
    }
}
