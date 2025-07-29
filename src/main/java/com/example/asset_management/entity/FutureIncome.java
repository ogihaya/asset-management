package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.YearMonth;
import jakarta.persistence.Convert;

@Entity
@Table(name = "future_incomes")
@Data
public class FutureIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int startAmount;

    private int endAmount;
    
    @Convert(converter = YearMonthConverter.class)
    private YearMonth startMonth;

    @Convert(converter = YearMonthConverter.class)
    private YearMonth endMonth;
}
