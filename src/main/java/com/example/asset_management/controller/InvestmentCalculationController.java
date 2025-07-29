package com.example.asset_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.service.calcInvest;
import com.example.asset_management.entity.InvestmentCalculation;
import com.example.asset_management.repository.InvestmentCalculationRepository;
import java.time.YearMonth;
import java.util.Map;

@Controller
public class InvestmentCalculationController {
    
    @Autowired
    private calcInvest calcInvestService;
    
    @Autowired
    private InvestmentCalculationRepository calculationRepository;
    
    @PostMapping("/calculate-investment")
    public String calculateInvestment(@RequestParam String targetMonth) {
        try {
            // 計算を実行
            Map<String, Object> result = calcInvestService.calculateInvestmentStrategy();

            calculationRepository.deleteAll();
            
            // 結果をデータベースに保存
            InvestmentCalculation calculation = new InvestmentCalculation();
            calculation.setOptimalMonth((YearMonth) result.get("optimalMonth"));
            calculation.setInvestmentAmount((Double) result.get("minInvestmentAmount"));
            
            calculationRepository.save(calculation);
            
            // ホーム画面にリダイレクト
            return "redirect:/?yearMonth=" + targetMonth;
            
        } catch (Exception e) {
            // エラーの場合もホーム画面にリダイレクト
            return "redirect:/?yearMonth=" + targetMonth + "&error=計算に失敗しました";
        }
    }
} 