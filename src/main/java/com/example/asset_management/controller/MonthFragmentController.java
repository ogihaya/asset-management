package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.repository.IncomeDataRepository;
import com.example.asset_management.repository.AssetDataRepository;
import com.example.asset_management.repository.AssetMasterRepository;
import com.example.asset_management.entity.IncomeData;
import com.example.asset_management.entity.AssetData;
import com.example.asset_management.entity.AssetMaster;
import com.example.asset_management.entity.InvestData;
import com.example.asset_management.entity.InvestMaster;
import com.example.asset_management.repository.InvestDataRepository;
import com.example.asset_management.repository.InvestMasterRepository;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MonthFragmentController {

    @Autowired
    private IncomeDataRepository incomeDataRepository;

    @Autowired
    private AssetDataRepository assetDataRepository;

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @Autowired
    private InvestDataRepository investDataRepository;

    @Autowired
    private InvestMasterRepository investMasterRepository;

    @GetMapping("/api/month-fragment")
    public String getMonthFragment(@RequestParam String yearMonth, Model model) {
        try {
            
            // 1. パラメータをYearMonthに変換
            YearMonth targetYearMonth = YearMonth.parse(yearMonth);
            
            // 2. データを取得
            IncomeData income = incomeDataRepository.findByTargetMonth(targetYearMonth).orElse(null);
            List<AssetData> assets = assetDataRepository.findByTargetMonthOrderByAssetMaster_AssetName(targetYearMonth);
            List<AssetMaster> allAssetMasters = assetMasterRepository.findAll();
            
            // 3. 既存の資産データをマップ化
            Map<Long, AssetData> existingAssetDataMap = assets.stream()
                .collect(Collectors.toMap(
                    assetData -> assetData.getAssetMaster().getId(),
                    assetData -> assetData
                ));
            
            // 4. モデルにデータを追加
            model.addAttribute("targetMonthIncome", income);
            model.addAttribute("targetMonthAssets", assets);
            model.addAttribute("allAssetMasters", allAssetMasters);
            model.addAttribute("existingAssetDataMap", existingAssetDataMap);
            model.addAttribute("targetYearMonth", targetYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")));

            List<InvestData> invests = investDataRepository.findByTargetMonthOrderByInvestMaster_InvestName(targetYearMonth);
            List<InvestMaster> allInvestMasters = investMasterRepository.findAll();
            
            // 3. 既存の資産データをマップ化
            Map<Long, InvestData> existingInvestDataMap = invests.stream()
                .collect(Collectors.toMap(
                    investData -> investData.getInvestMaster().getId(),
                    investData -> investData
                ));

            model.addAttribute("targetMonthInvests", invests);
            model.addAttribute("allInvestMasters", allInvestMasters);
            model.addAttribute("existingInvestDataMap", existingInvestDataMap);
            
            // 5. 部分テンプレートを返す
            return "home-fragments/month-content :: monthContent";
            
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error", "データの取得に失敗しました: " + e.getMessage());
            return "home-fragments/error :: errorMessage";
        }
    }
} 