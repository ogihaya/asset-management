package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.asset_management.repository.IncomeDataRepository;
import java.time.YearMonth;
import com.example.asset_management.entity.IncomeData;
import com.example.asset_management.entity.AssetData;
import java.util.List;
import com.example.asset_management.repository.AssetDataRepository;
import com.example.asset_management.entity.AssetMaster;
import com.example.asset_management.repository.AssetMasterRepository;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AssetController {


    @Autowired
    private IncomeDataRepository incomeDataRepository;

    @Autowired
    private AssetDataRepository assetDataRepository;

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @GetMapping("/")
    public String showAssetList(Model model) {

        YearMonth currentYearMonth = YearMonth.now();

        IncomeData currentMonthIncome = incomeDataRepository.findByTargetMonth(currentYearMonth).orElse(null);

        List<AssetData> currentMonthAssets = assetDataRepository.findByTargetMonthOrderByAssetMaster_AssetName(currentYearMonth);

        List<AssetMaster> allAssetMasters = assetMasterRepository.findAll();

        Map<Long, AssetData> existingAssetDataMap = currentMonthAssets.stream()
            .collect(Collectors.toMap(
                assetData -> assetData.getAssetMaster().getId(),
                assetData -> assetData
            ));

        model.addAttribute("currentMonthIncome", currentMonthIncome);
        model.addAttribute("currentYearMonth", currentYearMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")));
        model.addAttribute("currentMonthAssets", currentMonthAssets);
        model.addAttribute("allAssetMasters", allAssetMasters);
        model.addAttribute("existingAssetDataMap", existingAssetDataMap);

        return "home";
    }
}
