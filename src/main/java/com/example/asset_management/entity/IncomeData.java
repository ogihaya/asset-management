package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.YearMonth;
import jakarta.persistence.Convert;

@Entity
@Table(name = "income_data")
@Data
public class IncomeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @Convert(converter = YearMonthConverter.class)
    private YearMonth targetMonth;
}
