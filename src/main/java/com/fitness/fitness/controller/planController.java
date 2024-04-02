package com.fitness.fitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class planController {
    @GetMapping("/browse_plans")
    public String browsePlans() {
        return "plans";
    }
    @GetMapping("/chooseSilverPlan")
    public String chooseSilverPlan(){
        return "silverPlan";
    }
    @GetMapping("/chooseGoldPlan")
    public String chooseGoldPlan(){
        return "goldPlan";
    }
    @GetMapping("/chooseDiamondPlan")
    public String chooseDiamondPlan(){
        return "diamondPlan";
    }
    
}
