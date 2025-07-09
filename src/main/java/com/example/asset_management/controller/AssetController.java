package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String showAssetList(
        @RequestParam(value = "yearMonth", required = false) String yearMonthParam,
        Model model) {

        YearMonth targetYearMonth;
        if(yearMonthParam != null && !yearMonthParam.isEmpty()) {
            targetYearMonth = YearMonth.parse(yearMonthParam);
        } else {
            targetYearMonth = YearMonth.now();
        }

        IncomeData targetMonthIncome = incomeDataRepository.findByTargetMonth(targetYearMonth).orElse(null);

        List<AssetData> targetMonthAssets = assetDataRepository.findByTargetMonthOrderByAssetMaster_AssetName(targetYearMonth);

        List<AssetMaster> allAssetMasters = assetMasterRepository.findAll();

        Map<Long, AssetData> existingAssetDataMap = targetMonthAssets.stream()
            .collect(Collectors.toMap(
                assetData -> assetData.getAssetMaster().getId(),
                assetData -> assetData
            ));

        YearMonth previousYearMonth = targetYearMonth.minusMonths(1);
        YearMonth nextYearMonth = targetYearMonth.plusMonths(1);

        model.addAttribute("targetMonthIncome", targetMonthIncome);
        model.addAttribute("targetYearMonth", targetYearMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")));
        model.addAttribute("targetMonthAssets", targetMonthAssets);
        model.addAttribute("allAssetMasters", allAssetMasters);
        model.addAttribute("existingAssetDataMap", existingAssetDataMap);
        model.addAttribute("previousYearMonth", previousYearMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")));
        model.addAttribute("nextYearMonth", nextYearMonth.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")));

        return "home";
    }
}
