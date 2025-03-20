package com.dhruv892.SplitIt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBalanceDTO {

    private String user1;
    private String user2;

    private Double balance;
}
