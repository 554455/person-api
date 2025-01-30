package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.UserHistory;
import com.umaraliev.personapi.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    public List<UserHistory> getUserHistoryRepository() {
        return userHistoryRepository.findAll();
    }

    public UserHistory saveUserHistory(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }

    public UserHistory updateUserHistory(UserHistory userHistory) {
        return userHistoryRepository.save(userHistory);
    }

    public Optional<UserHistory> getUserHistoryById(UUID id) {
        return Optional.ofNullable(userHistoryRepository.findByUserId(id));
    }
}
