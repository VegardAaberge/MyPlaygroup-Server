package com.myplaygroup.server.chat.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.response.MessageResponseError;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.security.AuthorizationService;
import com.myplaygroup.server.security.model.UserInfo;
import com.myplaygroup.server.shared.utils.Constants;
import com.myplaygroup.server.shared.utils.UrlUtils;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
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

    public void sendMessage(WebSocketSession session, TextMessage message) throws IOException {
        ObjectReader reader = new ObjectMapper().findAndRegisterModules().reader();
        MessageRequest request = reader.readValue(message.getPayload(), MessageRequest.class);

        try {
            String usernameFromSession = getUsernameFromSession(session);
            boolean usernameIsValid = request.createdBy.equals(usernameFromSession) || request.readBy.contains(usernameFromSession);
            if(!usernameIsValid){
                throw new IllegalStateException();
            }

            String messageJson = chatService.storeMessage(
                    request
            );

            List<Member> receivingMembers = members
                    .values().stream()
                    .filter(m -> shouldReceiveMessage(m, request.receivers, request.createdBy))
                    .collect(Collectors.toList());

            receivingMembers.forEach(member -> {
                try {
                    if (member.getSocket().isOpen()) {
                        member.getSocket().sendMessage(new TextMessage(messageJson));
                    } else {
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

        try {
            String username = getUsernameFromSession(session);
            Map<String, List<String>> parameters = UrlUtils.splitQuery(session.getUri());
            List<String> listenTo = parameters.get("listen");

            members.put(session.getId(), new Member(
                    username,
                    session.getId(),
                    session,
                    listenTo
            ));
        } catch (UnsupportedEncodingException e) {
            throw new ServerErrorException("Could not init session");
        }
    }

    public void disconnectMember(WebSocketSession session) {
        String username = getUsernameFromSession(session);

        log.info("Disconnect " + username);
        Member member = members.get(session.getId());
        if(member != null && members.containsKey(username) && member.getSocket() != null){
            members.remove(username);
        }
    }

    private Boolean shouldReceiveMessage(Member member, List<String> receivers, String username){

        // Should receive its own messages
        if(Objects.equals(member.getUsername(), username))
            return true;

        // If receive all is found, then listen to all messages
        if(member.getListenTo().stream().anyMatch(x -> x.equals(Constants.RECEIVE_ALL)))
            return true;

        // Should receive if both user and member are receivers
        if(receivers.contains(member.getUsername()) && member.getListenTo().contains(username))
            return true;

        return false;
    }

    private String getUsernameFromSession(WebSocketSession session){
        String authorizationHeader = session.getHandshakeHeaders().get("cookie").get(0);
        UserInfo userInfo = authorizationService.getUserInfoFromRequest(authorizationHeader);

        return userInfo.getUsername();
    }
}
