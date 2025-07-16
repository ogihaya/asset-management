package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.example.asset_management.repository.FutureIncomeRepository;
import com.example.asset_management.repository.MonthlyExpenseRepository;
import com.example.asset_management.repository.FutureExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SettingController {
    @Autowired
    private FutureIncomeRepository futureIncomeRepository;
    @Autowired
    private MonthlyExpenseRepository monthlyExpenseRepository;
    @Autowired
    private FutureExpenseRepository futureExpenseRepository;
    @GetMapping("/setting")
    public String showSetting(Model model) {
        model.addAttribute("futureIncomes", futureIncomeRepository.findAll());
        model.addAttribute("monthlyExpenses", monthlyExpenseRepository.findAll());
        model.addAttribute("futureExpenses", futureExpenseRepository.findAll());
        return "setting";
    }
}
