package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.YearMonth;

@Entity
@Table(name = "future_incomes")
@Data
public class FutureIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int startAmount;

    private int endAmount;
    
    private YearMonth startMonth;

    private YearMonth endMonth;
}
