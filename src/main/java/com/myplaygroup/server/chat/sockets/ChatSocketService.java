package com.myplaygroup.server.chat.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.exception.ServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatSocketService {

    @Autowired
    ChatService chatService;

    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        String username = getUsernameFromSession(session);

        ObjectReader reader = new ObjectMapper().findAndRegisterModules().reader();
        MessageRequest request = reader.readValue(message.getPayload(), MessageRequest.class);

        String messageJson = chatService.storeMessage(
                username,
                request.message,
                request.receivers
        );

        members.values().forEach(member -> {
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
        List<NameValuePair> params = URLEncodedUtils.parse(Objects.requireNonNull(session.getUri()), StandardCharsets.UTF_8);
        Map<String, String> stringMap = params.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        return stringMap.get("username");
    }
}
