package com.dhruv892.ShareIt.repository;

import com.dhruv892.ShareIt.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
