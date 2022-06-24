package com.myplaygroup.server.chat.repository;

import com.myplaygroup.server.chat.model.Message;
import com.myplaygroup.server.chat.response.MessageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(
            nativeQuery = true,
            value = MessageQuery.QUERY_OWNER_AND_RECEIVER
    )
    List<MessageResponse> findByOwnerAndReceiver(Long userID);

    @Query(
            nativeQuery = true,
            value = MessageQuery.QUERY_ALL
    )
    List<MessageResponse> getAllMessageResponseItems();


    @Query(
            nativeQuery = true,
            value = MessageQuery.QUERY_BY_ID)
    Optional<MessageResponse> findMessageResponseById(Long messageId);
}
