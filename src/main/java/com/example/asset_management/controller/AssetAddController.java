package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.entity.AssetMaster;
import com.example.asset_management.repository.AssetMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AssetAddController {

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @PostMapping("/add-asset")
    public String AddAsset(@RequestParam("assetName") String assetName, @RequestParam("targetMonth") String targetMonth) {
        AssetMaster newAsset = new AssetMaster();
        newAsset.setAssetName(assetName.trim());

        assetMasterRepository.save(newAsset);

        return "redirect:/?yearMonth="+targetMonth;
    }
    
}
