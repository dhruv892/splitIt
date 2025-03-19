package com.dhruv892.ShareIt.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ExpenseResponseDTO {
    private Map<String, Double> userGets = new HashMap<>();
}
