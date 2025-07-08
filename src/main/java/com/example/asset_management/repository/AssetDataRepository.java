package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.AssetData;
import java.util.List;
import java.time.YearMonth;

@Repository
public interface AssetDataRepository extends JpaRepository<AssetData, Long>{

    List<AssetData> findByTargetMonth(YearMonth targetMonth);

    // 資産マスター情報も一緒に取得するメソッドを追加
    List<AssetData> findByTargetMonthOrderByAssetMaster_AssetName(YearMonth targetMonth);
}
