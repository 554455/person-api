package com.umaraliev.personapi.repository;

import com.umaraliev.personapi.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserHistoryRepository extends JpaRepository<UserHistory, UUID> {
}
