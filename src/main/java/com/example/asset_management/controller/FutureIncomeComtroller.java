package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.YearMonth;
import com.example.asset_management.entity.FutureIncome;
import com.example.asset_management.repository.FutureIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class FutureIncomeComtroller {
    @Autowired
    private FutureIncomeRepository futureIncomeRepository;

    @PostMapping("/setting/future-income")
    public String createFutureIncome(@RequestParam("startMonth") YearMonth startMonth, @RequestParam("endMonth") YearMonth endMonth, @RequestParam("startAmount") int startAmount, @RequestParam("endAmount") int endAmount) {
        FutureIncome futureIncome = new FutureIncome();
        futureIncome.setStartMonth(startMonth);
        futureIncome.setEndMonth(endMonth);
        futureIncome.setStartAmount(startAmount);
        futureIncome.setEndAmount(endAmount);
        futureIncomeRepository.save(futureIncome);
        return "redirect:/setting";
    }
}
