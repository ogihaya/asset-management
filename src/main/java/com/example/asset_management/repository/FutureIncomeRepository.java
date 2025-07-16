package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.FutureIncome;

@Repository
public interface FutureIncomeRepository extends JpaRepository<FutureIncome, Long>{

}
