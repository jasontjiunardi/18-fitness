package com.fitness.fitness.service;


import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.repository.PlanRepo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class PlanService {

    private final PlanRepo planRepo;

    public PlanService(PlanRepo planRepo) {
        this.planRepo = planRepo;
    }

    public List<String> findDistinctPlanTypes() {
        return planRepo.findDistinctPlanTypes();
    }

    public List<Plan> findByPlanType(String planType) {
        return planRepo.findByPlanType(planType);
    }
    public Set<Benefit> findBenefitsByPlanType(String planType) {
        return planRepo.findByPlanType(planType).stream()
           .flatMap(plan -> plan.getBenefits().stream())
           .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Benefit::getDescription))));
    }
    public Map<String, Double> getStartingPricesForAllPlanTypes() {
        List<Plan> allPlans = planRepo.findAll();
        Map<String, Double> startingPrices = new HashMap<>();
        allPlans.forEach(plan -> 
            startingPrices.merge(plan.getPlanType(), plan.getPrice(), Math::min));
        return startingPrices; 
    }
}
