package com.fitness.fitness.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.service.PlanService;




@Controller
public class PlanController {

    @Autowired
    PlanService planService;

    @GetMapping("/browse_plans")
    public String browsePlans(Model model) {
        Map<String, Double> startingPrices = planService.getStartingPricesForAllPlanTypes();
        
        // Custom sorting logic to ensure it is sorted by price, but can be customized further if needed
        LinkedHashMap<String, Double> sortedStartingPrices = startingPrices.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                
        List<String> sortedPlanTypes = new ArrayList<>(sortedStartingPrices.keySet());

        Map<String, Set<Benefit>> planBenefits = new HashMap<>();
        sortedPlanTypes.forEach(planType -> {
            Set<Benefit> benefits = planService.findBenefitsByPlanType(planType);
            planBenefits.put(planType, benefits);
        });

        model.addAttribute("planTypes", sortedPlanTypes);
        model.addAttribute("startingPrices", sortedStartingPrices); // Pass sorted prices for consistent display
        model.addAttribute("planBenefits", planBenefits);
        return "plans";
    }

    @GetMapping("/browsePlan/{planType}")
    public String browsePlan(@PathVariable("planType") String planType, Model model) {
        List<Plan> plans = planService.findByPlanType(planType);
        model.addAttribute("planType", planType);
        model.addAttribute("plans", plans);
        return "planDetails";
    }
}
