package com.example.asset_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.asset_management.repository.AssetDataRepository;
import com.example.asset_management.repository.IncomeDataRepository;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.YearMonth;
import java.util.Optional;
import java.time.format.DateTimeFormatter;

@RestController
public class GraphController {

    @Autowired
    private AssetDataRepository assetDataRepository;
    @Autowired
    private IncomeDataRepository incomeDataRepository;

    @GetMapping("/api/graph/data")
    public Map<String, Object> getGraphData(
        @RequestParam("type") String type,
        @RequestParam(value = "assetId", required = false) Long assetId) {
        
        List<Map<String, Object>> data = new ArrayList<>();

        switch (type) {
            case "income":
                data = getIncomeData();
                break;
            case "total-assets":
                data = getTotalAssetsData();
                break;
            case "specific-asset":
                data = getSpecificAssetData(assetId);
                break;
            default:
                data = getTotalAssetsData();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("type", type);
        return response;
    }
    
    private List<Map<String, Object>> getIncomeData() {
        List<YearMonth> monthsWithData = incomeDataRepository.findAllMonthsOrdered();
        List<Map<String, Object>> data = new ArrayList<>();

        for (YearMonth month : monthsWithData) {
            Optional<Integer> incomeAmount = incomeDataRepository.findAmountByTargetMonth(month);

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            monthData.put("amount", incomeAmount.orElse(0));
            data.add(monthData);
        }
        return data;
    }

    private List<Map<String, Object>> getTotalAssetsData() {
        List<YearMonth> monthsWithData = incomeDataRepository.findAllMonthsOrdered();
        List<Map<String, Object>> data = new ArrayList<>();

        for (YearMonth month : monthsWithData) {
            Optional<Integer> incomeAmount = incomeDataRepository.findAmountByTargetMonth(month);
            int income = incomeAmount.isPresent() ? incomeAmount.get() : 0;

            List<Integer> aasetAmountList = assetDataRepository.findAmountByTargetMonth(month);
            int assetAmount = aasetAmountList.stream()
                .mapToInt(Integer::intValue)
                .sum();

            int totalAmount = income + assetAmount;
                
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            monthData.put("amount", totalAmount);
            data.add(monthData);
            
        }
        return data;
    }

    private List<Map<String, Object>> getSpecificAssetData(Long assetId) {
        List<YearMonth> monthsWithData = assetDataRepository.findAllMonthsOrdered();
        List<Map<String, Object>> data = new ArrayList<>();

        for (YearMonth month : monthsWithData) {
            Optional<Integer> targetAssetAmount = assetDataRepository.findAmountByTargetMonthAndAssetMasterId(month, assetId);

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month.format(DateTimeFormatter.ofPattern("yyyy-MM")));
            monthData.put("amount", targetAssetAmount);
            data.add(monthData);
        }
        return data;
    }
}
