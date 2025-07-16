package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.YearMonth;

@Entity
@Table(name = "future_expenses")
@Data
public class FutureExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expenseName;

    private int amount;

    private YearMonth targetMonth;
}
