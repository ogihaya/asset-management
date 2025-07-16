package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.YearMonth;
import com.example.asset_management.entity.FutureExpense;
import com.example.asset_management.repository.FutureExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class FutureExpenseController {
    @Autowired
    private FutureExpenseRepository futureExpenseRepository;

    @PostMapping("/setting/future-expense")
    public String createFutureExpense(@RequestParam("expenseName") String expenseName, @RequestParam("amount") int amount, @RequestParam("targetMonth") YearMonth targetMonth) {
        FutureExpense futureExpense = new FutureExpense();
        futureExpense.setExpenseName(expenseName);
        futureExpense.setAmount(amount);
        futureExpense.setTargetMonth(targetMonth);
        futureExpenseRepository.save(futureExpense);
        return "redirect:/setting";
    }
}
