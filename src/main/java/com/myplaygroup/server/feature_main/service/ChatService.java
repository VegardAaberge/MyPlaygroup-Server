package com.myplaygroup.server.feature_main.service;

import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_login.service.AppUserService;
import com.myplaygroup.server.feature_main.model.Message;
import com.myplaygroup.server.feature_main.repository.MessageRepository;
import com.myplaygroup.server.feature_main.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {

    private final AppUserService appUserService;
    private final MessageRepository messageRepository;

    public List<MessageResponse> findByUsernameAndRecipient(String username) {
        AppUser appUser = appUserService.loadUserByUsername(username);

        List<MessageResponse> messages = messageRepository.findByOwnerAndReceiver(appUser.getId())
                .orElseThrow(() -> new IllegalStateException("test"));

        return messages;
    }

    public MessageResponse storeMessage(String username,
                               String message,
                               List<String> receivers) {

        AppUser appUser = appUserService.loadUserByUsername(username);

        List<AppUser> receiversUsers = new ArrayList<AppUser>();

        receivers.forEach(receiver -> {
            AppUser receiverUser = appUserService.loadUserByUsername(receiver);
            if(receiverUser.getUsername() == appUser.getUsername()){
                throw new IllegalStateException("Not allowed to add app user as a receiver");
            }
            receiversUsers.add(receiverUser);
        });

        Message messageEntity = new Message(
                message,
                appUser,
                receiversUsers,
                LocalDateTime.now()
        );

        messageRepository.save(messageEntity);

        MessageResponse messageResponse = messageRepository.findMessageResponseById(
                messageEntity.getId()
        ).orElseThrow(() -> new NotFoundException("Message was not added"));

        return messageResponse;
    }
}
