package com.dhruv892.SplitIt.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ExpenseDTO {
    private String description;
    private Double amount;
    private Long paidBy;
    private Set<String> members;

    private Long groupId;
}
