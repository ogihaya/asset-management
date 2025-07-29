package com.example.asset_management.repository;

import com.example.asset_management.entity.InvestmentCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InvestmentCalculationRepository extends JpaRepository<InvestmentCalculation, Long> {
     // 最新の投資計算データを1件取得するメソッド
     Optional<InvestmentCalculation> findFirstByOrderByIdDesc();
} 