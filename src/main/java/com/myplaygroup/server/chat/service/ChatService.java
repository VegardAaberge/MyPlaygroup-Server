package com.myplaygroup.server.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myplaygroup.server.chat.response.MessageResponseItem;
import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.exception.ServerErrorException;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import com.myplaygroup.server.chat.model.Message;
import com.myplaygroup.server.chat.repository.MessageRepository;
import com.myplaygroup.server.chat.response.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AppUserService appUserService;
    private final MessageRepository messageRepository;

    public List<MessageResponse> findByUsernameAndRecipient(String username) {
        AppUser appUser = appUserService.loadUserByUsername(username);

        return messageRepository.findByOwnerAndReceiver(appUser.getId());
    }

    public String storeMessage(String username,
                               String clientId,
                               String message,
                               List<String> receivers) throws JsonProcessingException {

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
                clientId,
                message,
                appUser,
                receiversUsers,
                LocalDateTime.now()
        );

        messageRepository.save(messageEntity);

        MessageResponse messageResponse = messageRepository.findMessageResponseById(
                messageEntity.getId()
        ).orElseThrow(() -> new NotFoundException("Message was not added"));

        ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(messageResponse);

        return json;
    }

    public List<MessageResponseItem> getAllMessages() {
        List<Message> messages = messageRepository.findAll();

        List<MessageResponseItem> messageResponseItems = messages.stream().map(item -> {

            List<String> receivers = item.getReceivers().stream()
                    .map(AppUser::getUsername)
                    .collect(Collectors.toList());

            return new MessageResponseItem(
                    item.getId(),
                    item.getClientId(),
                    item.getMessage(),
                    item.getCreatedBy().getUsername(),
                    receivers,
                    item.getCreated()
            );
        }).collect(Collectors.toList());

        return messageResponseItems;
    }
}
