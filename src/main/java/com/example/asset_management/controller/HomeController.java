package com.example.asset_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")//@GetMappingは/にアクセスしたときに実行するという意味
    public String home() {
        return "home";
    }
}
