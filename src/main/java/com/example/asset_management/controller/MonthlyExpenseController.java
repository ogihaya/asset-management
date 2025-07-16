package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.YearMonth;
import com.example.asset_management.entity.MonthlyExpense;
import com.example.asset_management.repository.MonthlyExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MonthlyExpenseController {
    @Autowired
    private MonthlyExpenseRepository monthlyExpenseRepository;

    @PostMapping("/setting/monthly-expense")
    public String createMonthlyExpense(@RequestParam("startMonth") YearMonth startMonth, @RequestParam("endMonth") YearMonth endMonth, @RequestParam("amount") int amount) {
        MonthlyExpense monthlyExpense = new MonthlyExpense();
        monthlyExpense.setStartMonth(startMonth);
        monthlyExpense.setEndMonth(endMonth);
        monthlyExpense.setAmount(amount);
        monthlyExpenseRepository.save(monthlyExpense);
        return "redirect:/setting";
    }
}
