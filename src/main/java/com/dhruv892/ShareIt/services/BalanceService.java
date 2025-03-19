package com.dhruv892.ShareIt.services;

import com.dhruv892.ShareIt.dto.GroupBalanceDTO;
import com.dhruv892.ShareIt.dto.UserBalanceDTO;
import com.dhruv892.ShareIt.entities.UserEntity;
import com.dhruv892.ShareIt.repository.GroupBalanceRepository;
import com.dhruv892.ShareIt.repository.UserBalanceRepository;
import com.dhruv892.ShareIt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final UserRepository userRepository;
    private final UserBalanceRepository userBalanceRepository;
    private final GroupBalanceRepository groupBalanceRepository;

    public List<UserBalanceDTO> getUserBalances(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userBalanceRepository.findByUser1OrUser2(user, user).stream()
                .map(balance -> new UserBalanceDTO(
                        balance.getUser1().getEmail(),
                        balance.getUser2().getEmail(),
                        balance.getBalance()
                ))
                .collect(Collectors.toList());
    }

    public List<GroupBalanceDTO> getUserGroupBalances(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return groupBalanceRepository.findByUser(user).stream()
                .map(balance -> new GroupBalanceDTO(
                        balance.getGroup().getName(),
                        balance.getBalance()
                ))
                .collect(Collectors.toList());
    }
}
