package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.AssetData;
import java.util.List;
import java.time.YearMonth;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface AssetDataRepository extends JpaRepository<AssetData, Long>{

    List<AssetData> findByTargetMonth(YearMonth targetMonth);

    // 資産マスター情報も一緒に取得するメソッドを追加
    List<AssetData> findByTargetMonthOrderByAssetMaster_AssetName(YearMonth targetMonth);

    @Query("SELECT DISTINCT a.targetMonth FROM AssetData a ORDER BY a.targetMonth")
    List<YearMonth> findAllMonthsOrdered();

    @Query("SELECT a.amount FROM AssetData a WHERE a.targetMonth = :targetMonth")
    List<Integer> findAmountByTargetMonth(YearMonth targetMonth);

    @Query("SELECT a.amount FROM AssetData a WHERE a.targetMonth = :targetMonth AND a.assetMaster.id = :assetMasterId")
    Optional<Integer> findAmountByTargetMonthAndAssetMasterId(YearMonth targetMonth, Long assetMasterId);
}
