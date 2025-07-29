package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.YearMonth;
import jakarta.persistence.Convert;

@Entity
@Table(name = "asset_data")
@Data
public class AssetData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne//多対一の関係を表す
    @JoinColumn(name = "asset_masters_id")//資産マスターのidを外部キーとして使用する
    private AssetMaster assetMaster;

    private int amount;//資産の金額

    @Convert(converter = YearMonthConverter.class)
    private YearMonth targetMonth;//資産の対象月
}
