package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.UserHistory;
import com.umaraliev.personapi.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
