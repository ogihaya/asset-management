package com.example.asset_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class calcInvest {
    
    @Autowired
    private calcIncome calcIncome;
    
    @Autowired
    private clacExpense clacExpense;
    
    /**
     * 投資戦略を計算
     * @return 投資戦略の結果（最適な投資額と月）
     */
    public Map<String, Object> calculateInvestmentStrategy() {
        // 累積収入と累積支出を取得
        Map<YearMonth, Integer> cumulativeIncome = calcIncome.calculateCumulativeIncome();
        Map<YearMonth, Integer> cumulativeExpenses = clacExpense.calculateCumulativeExpenses();

        
        // 現在の月を取得
        YearMonth currentMonth = YearMonth.now();
        
        // 各月の余力資産を計算
        Map<YearMonth, Integer> surplusAssets = calculateSurplusAssets(cumulativeIncome, cumulativeExpenses);
        
        // 各月の投資可能額を計算
        Map<YearMonth, Double> investmentAmounts = calculateInvestmentAmounts(surplusAssets, currentMonth);
        
        // 最適な投資戦略を取得
        return findOptimalInvestment(investmentAmounts);
    }
    
    /**
     * 各月の余力資産を計算
     */
    private Map<YearMonth, Integer> calculateSurplusAssets(
            Map<YearMonth, Integer> cumulativeIncome, 
            Map<YearMonth, Integer> cumulativeExpenses) {
        
        Map<YearMonth, Integer> surplusAssets = new HashMap<>();
        
        for (YearMonth month : cumulativeIncome.keySet()) {
            int income = cumulativeIncome.get(month);
            int expenses = cumulativeExpenses.get(month);
            int surplus = income - expenses;
            surplusAssets.put(month, surplus);
        }
        
        return surplusAssets;
    }
    
    /**
     * 各月の投資可能額を計算
     * 投資可能額 = 余力資産 / 現在月からその月までの月数
     */
    private Map<YearMonth, Double> calculateInvestmentAmounts(
            Map<YearMonth, Integer> surplusAssets, 
            YearMonth currentMonth) {
        
        Map<YearMonth, Double> investmentAmounts = new HashMap<>();
        
        for (YearMonth month : surplusAssets.keySet()) {
            int surplus = surplusAssets.get(month);
            
            // 現在月からその月までの月数を計算
            long monthsFromCurrent = ChronoUnit.MONTHS.between(currentMonth, month) + 1;
            
            // 投資可能額を計算
            double investmentAmount = (double) surplus / monthsFromCurrent;
            investmentAmounts.put(month, investmentAmount);
        }
        
        return investmentAmounts;
    }
    
    /**
     * 最適な投資戦略を取得
     */
    private Map<String, Object> findOptimalInvestment(Map<YearMonth, Double> investmentAmounts) {
        YearMonth optimalMonth = null;
        double minInvestmentAmount = Double.MAX_VALUE;
        
        // 最小の投資可能額とその月を探す
        for (Map.Entry<YearMonth, Double> entry : investmentAmounts.entrySet()) {
            YearMonth month = entry.getKey();
            double amount = entry.getValue();
            
            if (amount < minInvestmentAmount) {
                minInvestmentAmount = amount;
                optimalMonth = month;
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("optimalMonth", optimalMonth);
        result.put("minInvestmentAmount", minInvestmentAmount);
                
        return result;
    }
}