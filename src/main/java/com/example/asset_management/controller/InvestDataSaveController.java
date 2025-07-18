package com.example.asset_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.asset_management.entity.InvestData;
import com.example.asset_management.entity.InvestMaster;
import com.example.asset_management.repository.InvestDataRepository;
import com.example.asset_management.repository.InvestMasterRepository;
import java.time.YearMonth;

@Controller
public class InvestDataSaveController {
    
    @Autowired
    private InvestDataRepository investDataRepository;
    @Autowired
    private InvestMasterRepository investMasterRepository;

    @PostMapping("/invest-save")
    public String saveInvestData(@RequestParam("investMasterId") Long investMasterId,
                                @RequestParam("targetMonth") String targetMonthString,
                                @RequestParam("amount") int amount) {
        
        InvestMaster investMaster = investMasterRepository.findById(investMasterId).orElseThrow(() -> new RuntimeException("InvestMaster not found"));                       

        InvestData investData = new InvestData();
        investData.setInvestMaster(investMaster);
        investData.setTargetMonth(YearMonth.parse(targetMonthString));
        investData.setAmount(amount);

        investDataRepository.save(investData);

        return "redirect:/?yearMonth=" + targetMonthString;
    }
}
