package com.example.asset_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity//このクラスはデータベースのテーブルとして扱うという意味
@Table(name = "asset_masters")//テーブルの名前
@Data//getter,setter,toString,equals,hashCodeを自動生成する
public class AssetMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;//資産名
}
