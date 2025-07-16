package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.MonthlyExpense;

@Repository
public interface MonthlyExpenseRepository extends JpaRepository<MonthlyExpense, Long>{

}
