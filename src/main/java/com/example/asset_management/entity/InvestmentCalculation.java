package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.YearMonth;

@Entity
@Table(name = "investment_calculation")
@Data
public class InvestmentCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private YearMonth optimalMonth; // 最適な投資月
    private double investmentAmount; // 投資可能額
} 