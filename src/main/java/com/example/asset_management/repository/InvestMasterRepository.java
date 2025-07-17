package com.example.asset_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.InvestMaster;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface InvestMasterRepository extends JpaRepository<InvestMaster, Long> {
    @Query("SELECT i.investName FROM InvestMaster i")
    List<String> findInvestNameBy();
}
