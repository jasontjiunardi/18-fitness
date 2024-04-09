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
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.service.PlanService;








@Controller
public class planController {

    @Autowired
    private PlanService planService;
    @GetMapping("/browse_plans")
    public String browsePlans(Model model) {
        Map<String, Double> startingPrices = planService.getStartingPricesForAllPlanTypes();

        LinkedHashMap<String, Double> sortedStartingPrices = startingPrices.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue, // This merge function will keep the old value in case of a key collision
                LinkedHashMap::new));
                

        List<String> sortedPlanTypes = new ArrayList<>(sortedStartingPrices.keySet());
        Map<String, List<Benefit>> planBenefits = new HashMap<>();
        sortedPlanTypes.forEach(planType -> {
            List<Benefit> benefits = planService.findBenefitsByPlanType(planType).get(planType);
            planBenefits.put(planType, benefits);
        });

        model.addAttribute("planTypes", sortedPlanTypes);
        model.addAttribute("startingPrices", sortedStartingPrices);
        model.addAttribute("planBenefits", planBenefits);
        
        return "plans";

    }

    @GetMapping("/browsePlan/{planType}")
    public String browsePlan(@PathVariable("planType") String planType, Model model) {
        List<Plan> plans = planService.findByPlanType(planType);
        
        // Assuming each planType only has one set of details but multiple duration prices
        Plan planDetails = plans.stream().findFirst().orElseThrow(() -> new RuntimeException("Plan type not found"));
    
        List<Benefit> benefits = new ArrayList<>(planDetails.getBenefits()); // Assuming findBenefitsByPlanType method adjustment
    
        // If you want to include various prices for different durations directly:
        Set<PlanDurationPrice> durationPrices = planDetails.getPlanDurationPrices();
    
        model.addAttribute("planType", planType);
        model.addAttribute("planDetails", planDetails.getPlanDetails());
        model.addAttribute("benefits", benefits);
        model.addAttribute("durationPrices", durationPrices);
        return "planDetails";
    }

    @GetMapping("/browsePlan/{planType}/purchaseForm")
    public String showPurchaseForm(@PathVariable("planType") String planType,
                                   @RequestParam("duration") String duration, 
                                   @RequestParam("price") Double price, Model model) {
        model.addAttribute("planType", planType);
        model.addAttribute("selectedDuration", duration);
        model.addAttribute("price", price);
        return "purchaseForm";
    }
    
    
    
}
