package com.example.asset_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.asset_management.entity.FutureExpense;
import com.example.asset_management.entity.MonthlyExpense;
import com.example.asset_management.repository.FutureExpenseRepository;
import com.example.asset_management.repository.MonthlyExpenseRepository;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class clacExpense {
    @Autowired
    private FutureExpenseRepository futureExpenseRepository;

    @Autowired
    private MonthlyExpenseRepository monthlyExpenseRepository;

    /**
     * 現在の月から2100年12月までの各月の累積支出を計算する
     * @return 各月の累積支出額のマップ
     */
    public Map<YearMonth, Integer> calculateCumulativeExpenses() {
        // 現在の年月を取得
        YearMonth currentMonth = YearMonth.now();
        // 2100年12月を設定
        YearMonth endMonth = YearMonth.of(2100, 12);
        
        // 累積支出額のマップ
        Map<YearMonth, Integer> cumulativeExpenses = new HashMap<>();
        int cumulative = 0;
        
        // 現在の月から2100年12月までループ
        YearMonth current = currentMonth;
        while (!current.isAfter(endMonth)) {
            // その月の支出を計算
            int monthlyExpense = calculateMonthlyExpense(current);
            cumulative += monthlyExpense;
            cumulativeExpenses.put(current, cumulative);
            current = current.plusMonths(1);
        }
        
        return cumulativeExpenses;
    }

    /**
     * 特定月の支出を計算
     */
    private int calculateMonthlyExpense(YearMonth targetMonth) {
        int totalExpense = 0;
        
        // FutureExpenseから支出を取得
        List<FutureExpense> futureExpenses = futureExpenseRepository.findAll();
        for (FutureExpense expense : futureExpenses) {
            if (targetMonth.equals(expense.getTargetMonth())) {
                totalExpense += expense.getAmount();
            }
        }
        
        // MonthlyExpenseから支出を取得
        List<MonthlyExpense> monthlyExpenses = monthlyExpenseRepository.findAll();
        YearMonth currentMonth = YearMonth.now();
        
        for (MonthlyExpense expense : monthlyExpenses) {
            YearMonth startMonth = expense.getStartMonth();
            YearMonth endMonth = expense.getEndMonth();
            
            // 開始月が現在の月より前の場合は、現在の月から開始
            YearMonth actualStartMonth = startMonth.isBefore(currentMonth) ? currentMonth : startMonth;
            
            // 対象月が期間内にあるかチェック
            if (!targetMonth.isBefore(actualStartMonth) && !targetMonth.isAfter(endMonth)) {
                totalExpense += expense.getAmount();
            }
        }
        
        return totalExpense;
    }
}