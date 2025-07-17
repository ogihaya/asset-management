package com.example.asset_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.entity.InvestMaster;
import com.example.asset_management.repository.InvestMasterRepository;

@Controller
public class InvestAddController {

    @Autowired
    private InvestMasterRepository investMasterRepository;

    @PostMapping("/add-invest")
    public String AddInvest(@RequestParam("investName") String investName, @RequestParam("targetMonth") String targetMonth) {
        InvestMaster newInvest = new InvestMaster();
        newInvest.setInvestName(investName.trim());

        investMasterRepository.save(newInvest);

        return "redirect:/?yearMonth="+targetMonth;
    }
}
