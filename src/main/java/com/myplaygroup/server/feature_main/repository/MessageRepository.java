package com.myplaygroup.server.feature_main.repository;

import com.myplaygroup.server.feature_main.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long> {

}
