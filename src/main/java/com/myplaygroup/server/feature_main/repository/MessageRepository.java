package com.myplaygroup.server.feature_main.repository;

import com.myplaygroup.server.feature_login.model.AppUser;
import com.myplaygroup.server.feature_main.model.Message;
import com.myplaygroup.server.feature_main.response.MessageResponse;
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
            value = "SELECT DISTINCT " +
                    "    message.id, " +
                    "    message.created, " +
                    "    message.message, " +
                    "    app_user.username as createdBy, " +
                    "    app_user.profile_name as profileName " +
                    "FROM message " +
                    "    JOIN message_receivers  " +
                    "        ON message.id = message_receivers.message_id " +
                    "    JOIN app_user " +
                    "        ON app_user.id = message.app_user_id " +
                    "WHERE " +
                    "    message.app_user_id = ?1 " +
                    "    OR message_receivers.receivers_id = ?1"
    )
    Optional<List<MessageResponse>> findByOwnerAndReceiver(Long userID);
}
