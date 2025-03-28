package com.dhruv892.SplitIt.controllers;

import com.dhruv892.SplitIt.dto.GroupBalanceDTO;
import com.dhruv892.SplitIt.dto.UserBalanceDTO;
import com.dhruv892.SplitIt.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/user/{userId}")
    public List<UserBalanceDTO> getUserBalances(@PathVariable Long userId) {
        return balanceService.getUserBalances(userId);
    }

    @GetMapping("/group/{userId}")
    public List<GroupBalanceDTO> getUserGroupBalances(@PathVariable Long userId) {
        return balanceService.getUserGroupBalances(userId);
    }

}
