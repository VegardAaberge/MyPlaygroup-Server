package com.myplaygroup.server.chat.controller;

import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.chat.service.ChatService;
import com.myplaygroup.server.security.AuthorizationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/chat")
@AllArgsConstructor
public class ChatController {

    private ChatService chatService;
    private AuthorizationService authorizationService;

    @GetMapping
    public List<MessageResponse> getChatMessages(HttpServletRequest servletRequest){
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return chatService.findByUsernameAndRecipient(username);
    }

    @PostMapping
    public MessageResponse sendMessage(@RequestBody @Valid MessageRequest request, HttpServletRequest servletRequest){
        String username = authorizationService.getUserInfoFromRequest(servletRequest).getUsername();

        return chatService.storeMessage(
                username,
                request.message,
                request.receivers
        );
    }
}
