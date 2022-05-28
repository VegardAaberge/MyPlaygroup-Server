package com.myplaygroup.server.chat.sockets;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.response.MessageResponseError;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.security.model.UserInfo;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.repository.AppUserRepository;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
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

    private final AppUserService userService;
    private final ChatService chatService;
    private final AuthorizationService authorizationService;
    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException, JSONException {
        ObjectReader reader = new ObjectMapper().findAndRegisterModules().reader();
        MessageRequest request = reader.readValue(message.getPayload(), MessageRequest.class);

        try {
            String username = getUsernameFromSession(session);

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
                    if(member.getSocket().isOpen()){
                        member.getSocket().sendMessage(new TextMessage(messageJson));
                    }else {
                        members.remove(member.getSessionId());
                    }

                } catch (IOException e) {
                    throw new ServerErrorException("There was an error sending message from the socket");
                }
            });
        }catch (Exception e){
            if(!members.containsKey(session.getId()))
                throw e;

            Member member = members.get(session.getId());
            AppUser appUser = userService.loadUserByUsername(member.getUsername());

            if(member.getSocket().isOpen()){
                MessageResponseError errorResponse = new MessageResponseError(
                        request.clientId,
                        request.message,
                        appUser.getUsername(),
                        appUser.getProfileName()
                );

                ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(errorResponse);

                session.sendMessage(new TextMessage(json));
            }
        }
    }

    public void connectNewMember(WebSocketSession session) {
        String username = getUsernameFromSession(session);

        members.put(session.getId(), new Member(
                username,
                session.getId(),
                session
        ));
    }

    public void disconnectMember(WebSocketSession session) {
        String username = getUsernameFromSession(session);

        log.info("Disconnect " + username);
        Member member = members.get(session.getId());
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
