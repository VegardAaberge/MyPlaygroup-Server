package com.myplaygroup.server.user.repository;

import com.myplaygroup.server.user.model.AppUser;
import com.myplaygroup.server.user.response.AppUserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u")
    List<AppUserItem> getAllUsers();
    
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);
}
