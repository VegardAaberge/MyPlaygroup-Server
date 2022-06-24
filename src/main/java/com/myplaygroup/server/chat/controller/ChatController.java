package com.myplaygroup.server.chat.controller;

import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/chat")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;
    private AuthorizationService authorizationService;

    @GetMapping(path = "/user")
    public List<MessageResponse> getChatMessages(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return chatService.findByUsernameAndRecipient(username);
    }

    @GetMapping(path = "/admin")
    public List<MessageResponse> getAllChatMessages(HttpServletRequest servletRequest){
        log.info(servletRequest.getServletPath());

        return chatService.getAllMessages();
    }
}
