package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.entity.IncomeData;
import com.example.asset_management.repository.IncomeDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.YearMonth;

@Controller
public class IncomeSaveController {
    
    @Autowired
    private IncomeDataRepository incomeDataRepository;

    @PostMapping("/income-save")
    public String incomeSave(@RequestParam("income") int income, @RequestParam("targetMonth") String targetMonthString) {
        
        YearMonth targetMonth = YearMonth.parse(targetMonthString);
        IncomeData incomeData = incomeDataRepository.findByTargetMonth(targetMonth).orElse(new IncomeData());
        incomeData.setAmount(income);
        incomeData.setTargetMonth(targetMonth);
        incomeDataRepository.save(incomeData);

        return "redirect:/?yearMonth=" + targetMonthString;
        
    }
}
