package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.IncomeData;
import java.util.List;
import java.time.YearMonth;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

@Repository
public interface IncomeDataRepository extends JpaRepository<IncomeData, Long>{

    @Query("SELECT i.targetMonth FROM IncomeData i")
    List<YearMonth> findTargetMonthBy();

    Optional<IncomeData> findByTargetMonth(YearMonth targetMonth);
}
