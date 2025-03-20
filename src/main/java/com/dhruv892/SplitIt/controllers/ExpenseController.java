package com.dhruv892.SplitIt.controllers;

import com.dhruv892.SplitIt.dto.ExpenseDTO;
import com.dhruv892.SplitIt.dto.ExpenseResponseDTO;
import com.dhruv892.SplitIt.services.ExpenseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseResponseDTO addExpense(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.addExpense(expenseDTO);
    }
}
