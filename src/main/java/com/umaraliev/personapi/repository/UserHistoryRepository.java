package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserHistoryRepository extends JpaRepository<UserHistory, UUID> {

    @Query("SELECT uh FROM UserHistory uh WHERE uh.user.id = :userId")
    UserHistory findByUserId(@Param("userId") UUID userId);
}
