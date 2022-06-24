package com.myplaygroup.server.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.myplaygroup.server.chat.model.Message;
import com.myplaygroup.server.chat.repository.MessageRepository;
import com.myplaygroup.server.chat.requests.MessageRequest;
import com.myplaygroup.server.chat.response.MessageResponse;
import com.myplaygroup.server.exception.NotFoundException;
import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AppUserService appUserService;
    private final MessageRepository messageRepository;

    public List<MessageResponse> findByUsernameAndRecipient(String username) {
        AppUser appUser = appUserService.loadUserByUsername(username);

        return messageRepository.findByOwnerAndReceiver(appUser.getId());
    }

    public List<MessageResponse> getAllMessages() {
        List<Message> messages = messageRepository.findAll();

        return messageRepository.getAllMessageResponseItems();
    }

    public String storeMessage(MessageRequest request) throws JsonProcessingException {

        AppUser appUser = appUserService.loadUserByUsername(request.createdBy);

        List<AppUser> receiversUsers = new ArrayList<AppUser>();
        List<AppUser> readByUsers = new ArrayList<AppUser>();
        Message messageEntity = null;

        request.receivers.forEach(receiver -> {
            AppUser receiverUser = appUserService.loadUserByUsername(receiver);
            if(receiverUser.getUsername() == appUser.getUsername()){
                throw new IllegalStateException("Not allowed to add app user as a receiver");
            }
            receiversUsers.add(receiverUser);
        });

        request.readBy.forEach(receiver -> {
            AppUser readByUser = appUserService.loadUserByUsername(receiver);
            if(readByUser.getUsername() == appUser.getUsername()){
                throw new IllegalStateException("Not allowed to add app user as a receiver");
            }
            readByUsers.add(readByUser);
        });

        if(request.id == -1){
            messageEntity = new Message(
                    request.clientId,
                    request.message,
                    appUser,
                    receiversUsers,
                    readByUsers
            );

            messageRepository.save(messageEntity);
        }else {
            messageEntity = messageRepository.findById(request.id)
                    .orElseThrow(() -> new NotFoundException("Message not found"));

            messageEntity.setReadBy(readByUsers);

            messageRepository.save(messageEntity);
        }

        MessageResponse messageResponse = messageRepository.findMessageResponseById(
                messageEntity.getId()
        ).orElseThrow(() -> new NotFoundException("Message was not added"));

        ObjectWriter ow = new ObjectMapper().findAndRegisterModules().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(messageResponse);

        return json;
    }
}
