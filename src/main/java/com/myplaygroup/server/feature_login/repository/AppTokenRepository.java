package com.myplaygroup.server.feature_login.repository;

import com.myplaygroup.server.feature_login.model.AppToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppTokenRepository extends JpaRepository<AppToken, Long> {

    Optional<AppToken> findByToken(String token);
}
