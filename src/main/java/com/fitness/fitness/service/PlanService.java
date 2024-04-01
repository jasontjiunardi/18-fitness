package com.fitness.fitness.service;


import org.springframework.stereotype.Service;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.repository.PlanRepo;

import java.util.List;

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
}
