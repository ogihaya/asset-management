package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.asset_management.repository.AssetMasterRepository;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssetController {

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @GetMapping("/")
    public String showAssetList(Model model) {
        List<String> assetNames = assetMasterRepository.findAssetNameBy();
        model.addAttribute("assetNames", assetNames);
        return "home";
    }
}
