package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IncomeController {
    
    @PostMapping("/income")
    public String income(@RequestParam("income") int income) {
        return "income";
    }
}
