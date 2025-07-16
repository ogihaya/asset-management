package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.FutureExpense;

@Repository
public interface FutureExpenseRepository extends JpaRepository<FutureExpense, Long>{
}