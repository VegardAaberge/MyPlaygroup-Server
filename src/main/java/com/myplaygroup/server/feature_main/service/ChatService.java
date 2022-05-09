package com.myplaygroup.server.feature_main.service;

import com.myplaygroup.server.feature_login.service.AppUserService;
import com.myplaygroup.server.feature_main.model.Message;
import com.myplaygroup.server.feature_main.repository.MessageRepository;
import com.myplaygroup.server.feature_main.requests.MessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {

    private final AppUserService appUserService;
    private final MessageRepository messageRepository;

    public List<Message> findByUsernameAndRecipient(String username) {
        UserDetails userDetails = appUserService.loadUserByUsername(username);

        List<Message> messages = messageRepository.findAll();

        return messages;
    }

    public String storeMessage(String username,
                               String message,
                               List<String> receivers) {

        UserDetails userDetails = appUserService.loadUserByUsername(username);

        // Verify that the recipients exist
        receivers.forEach(receiver -> appUserService.loadUserByUsername(receiver));

        Message messageEntity = new Message(
                message,
                userDetails.getUsername(),
                receivers,
                LocalDateTime.now()
        );

        messageRepository.save(messageEntity);

        return "Successfully sent message";
    }
}
