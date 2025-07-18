package com.example.asset_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.InvestData;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.time.YearMonth;

@Repository
public interface InvestDataRepository extends JpaRepository<InvestData, Long> {
    
    List<InvestData> findByTargetMonth(YearMonth targetMonth);

    // 資産マスター情報も一緒に取得するメソッドを追加
    List<InvestData> findByTargetMonthOrderByInvestMaster_InvestName(YearMonth targetMonth);

    @Query("SELECT DISTINCT i.targetMonth FROM InvestData i ORDER BY i.targetMonth")
    List<YearMonth> findAllMonthsOrdered();

    @Query("SELECT i.amount FROM InvestData i WHERE i.targetMonth = :targetMonth")
    List<Integer> findAmountByTargetMonth(YearMonth targetMonth);

    @Query("SELECT i.amount FROM InvestData i WHERE i.targetMonth = :targetMonth AND i.investMaster.id = :investMasterId")
    Optional<Integer> findAmountByTargetMonthAndInvestMasterId(YearMonth targetMonth, Long investMasterId);
}
