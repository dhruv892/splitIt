package com.dhruv892.SplitIt.repository;

import com.dhruv892.SplitIt.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
