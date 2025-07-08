package com.example.asset_management.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.asset_management.entity.AssetMaster;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AssetMasterRepository extends JpaRepository<AssetMaster, Long>{

    @Query("SELECT a.assetName FROM AssetMaster a")
    List<String> findAssetNameBy();
    
}
