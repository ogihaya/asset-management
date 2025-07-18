package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.YearMonth;

@Entity
@Table(name = "invest_data")
@Data
public class InvestData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invest_master_id")
    private InvestMaster investMaster;
    
    private int amount;
    
    private YearMonth targetMonth;
}
    
