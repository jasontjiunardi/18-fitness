package com.fitness.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fitness.fitness.model.Plan;
import com.fitness.fitness.service.PlanService;




@Controller
public class planController {
    @Autowired
    PlanService planService;
    @GetMapping("/browse_plans")
    public String browsePlans(Model model) {
        model.addAttribute("planTypes", planService.findDistinctPlanTypes());
        return "plans";
    }

    @GetMapping("/browsePlan/{planType}")
    public String browsePlan(@PathVariable("planType")String planType, Model model) {
        List<Plan> plan = planService.findByPlanType(planType);
        
        model.addAttribute("planType", planType);
        model.addAttribute("plans", plan);
        return "planDetails";
    }
    
    
}
