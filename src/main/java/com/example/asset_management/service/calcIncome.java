package com.example.asset_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.asset_management.entity.FutureIncome;
import com.example.asset_management.repository.FutureIncomeRepository;
import com.example.asset_management.repository.IncomeDataRepository;
import com.example.asset_management.repository.AssetDataRepository;
import java.time.YearMonth;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class calcIncome {

    @Autowired
    private FutureIncomeRepository futureIncomeRepository;

    @Autowired
    private IncomeDataRepository incomeDataRepository;

    @Autowired
    private AssetDataRepository assetDataRepository;

    /**
     * 現在の月から2100年12月までの各月の累積収入を計算する
     * @return 各月の累積収入額のリスト（例：[300000, 650000, ...]）
     */
    public Map<YearMonth, Integer> calculateCumulativeIncome() {
        // 現在の年月を取得
        YearMonth currentMonth = YearMonth.now();
        // 2100年12月を設定
        YearMonth endMonth = YearMonth.of(2100, 12);
        
        // 累積収入額のリスト
        Map<YearMonth, Integer> cumulativeIncome = new HashMap<>();
        int cumulative = getPreviousMonthTotalAssets(currentMonth);
        
        // 現在の月から2100年12月までループ
        YearMonth current = currentMonth;
        while (!current.isAfter(endMonth)) {
            // その月の収入を計算（線形補間）
            int monthlyIncome = calculateMonthlyIncome(current);
            cumulative += monthlyIncome;
            cumulativeIncome.put(current, cumulative);
            current = current.plusMonths(1);
        }
        
        return cumulativeIncome;
    }

    /**
     * 特定月の収入を線形補間で計算
     */
    private int calculateMonthlyIncome(YearMonth targetMonth) {
        List<FutureIncome> futureIncomes = futureIncomeRepository.findAll();
        int totalIncome = 0;
        
        for (FutureIncome income : futureIncomes) {
            YearMonth startMonth = income.getStartMonth();
            YearMonth endMonth = income.getEndMonth();
            
            // 現在の月より前の開始月は、現在の月から開始
            YearMonth actualStartMonth = startMonth.isBefore(YearMonth.now()) ? 
                YearMonth.now() : startMonth;
            
            // 対象月が期間内にあるかチェック
            if (!targetMonth.isBefore(actualStartMonth) && !targetMonth.isAfter(endMonth)) {
                // 線形補間で収入を計算
                int interpolatedAmount = calculateLinearInterpolation(
                    income, targetMonth, actualStartMonth, endMonth);
                totalIncome += interpolatedAmount;
            }
        }
        
        return totalIncome;
    }

    /**
     * 線形補間で収入額を計算
     * @param income FutureIncomeエンティティ
     * @param targetMonth 対象月
     * @param startMonth 実際の開始月
     * @param endMonth 終了月
     * @return 補間された収入額
     */
    private int calculateLinearInterpolation(
            FutureIncome income, 
            YearMonth targetMonth, 
            YearMonth startMonth, 
            YearMonth endMonth) {
        
        // 開始金額と終了金額
        int startAmount = income.getStartAmount();
        int endAmount = income.getEndAmount();
        
        // 期間の月数を計算
        long totalMonths = ChronoUnit.MONTHS.between(startMonth, endMonth) + 1;
        
        // 対象月が開始月から何ヶ月目かを計算
        long monthsFromStart = ChronoUnit.MONTHS.between(startMonth, targetMonth);
        
        // 線形補間の計算
        // 公式: startAmount + (endAmount - startAmount) * (monthsFromStart / totalMonths)
        double ratio = (double) monthsFromStart / totalMonths;
        int interpolatedAmount = (int) Math.round(startAmount + (endAmount - startAmount) * ratio);
        
        return interpolatedAmount;
    }

      /**
     * 前月の総資産（収入 + 資産）を取得
     * @param targetMonth 対象月
     * @return 前月の総資産
     */
    public int getPreviousMonthTotalAssets(YearMonth targetMonth) {
        // 前月を計算
        YearMonth previousMonth = targetMonth.minusMonths(1);
        
        // 前月の収入を取得
        Optional<Integer> incomeAmount = incomeDataRepository.findAmountByTargetMonth(previousMonth);
        int income = incomeAmount.isPresent() ? incomeAmount.get() : 0;

        // 前月の資産を取得
        List<Integer> assetAmountList = assetDataRepository.findAmountByTargetMonth(previousMonth);
        int assetAmount = assetAmountList.stream()
            .mapToInt(Integer::intValue)
            .sum();

        // 収入 + 資産を返す（投資は含めない）
        return income + assetAmount;
    }
}
