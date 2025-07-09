package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.entity.AssetData;
import com.example.asset_management.entity.AssetMaster;
import com.example.asset_management.repository.AssetDataRepository;
import com.example.asset_management.repository.AssetMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.YearMonth;

@Controller
public class AssetDataSaveController {

    @Autowired
    private AssetDataRepository assetDataRepository;
    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @PostMapping("/asset-save")
    public String saveAssetData(@RequestParam("assetMasterId") Long assetMasterId,
                                @RequestParam("targetMonth") String targetMonth,
                                @RequestParam("amount") int amount) {
        
        AssetMaster assetMaster = assetMasterRepository.findById(assetMasterId).orElseThrow(() -> new RuntimeException("AssetMaster not found"));                       

        AssetData assetData = new AssetData();
        assetData.setAssetMaster(assetMaster);
        assetData.setTargetMonth(YearMonth.parse(targetMonth));
        assetData.setAmount(amount);

        assetDataRepository.save(assetData);

        return "redirect:/";
    }
    
}
