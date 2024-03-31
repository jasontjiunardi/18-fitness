package com.fitness.fitness.initializerdatabasedata;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitness.fitness.model.Plan;
import com.fitness.fitness.repository.PlanRepo;

import jakarta.annotation.PostConstruct;

public class InitializePlanData {
    @Autowired
    private PlanRepo planRepo;

    @PostConstruct
    public void init() {
        if (planRepo.count() == 0) { // Check if plans already exist
            Plan silverPlan6Months = new Plan();
            silverPlan6Months.setPlanType("Silver");
            silverPlan6Months.setDurationType(6);
            silverPlan6Months.setPrice(50.0);

            Plan silverPlan12Months = new Plan();
            silverPlan12Months.setPlanType("Silver");
            silverPlan12Months.setDurationType(12);
            silverPlan12Months.setPrice(90.0);

            Plan goldPlan6Months = new Plan();
            goldPlan6Months.setPlanType("Gold");
            goldPlan6Months.setDurationType(6);
            goldPlan6Months.setPrice(80.0);

            Plan goldPlan12Months = new Plan();
            goldPlan12Months.setPlanType("Gold");
            goldPlan12Months.setDurationType(12);
            goldPlan12Months.setPrice(150.0);

            Plan diamondPlan6Months = new Plan();
            diamondPlan6Months.setPlanType("Diamond");
            diamondPlan6Months.setDurationType(6);
            diamondPlan6Months.setPrice(120.0);

            Plan diamondPlan12Months = new Plan();
            diamondPlan12Months.setPlanType("Diamond");
            diamondPlan12Months.setDurationType(12);
            diamondPlan12Months.setPrice(220.0);

            planRepo.save(silverPlan6Months);
            planRepo.save(silverPlan12Months);
            planRepo.save(goldPlan6Months);
            planRepo.save(goldPlan12Months);
            planRepo.save(diamondPlan6Months);
            planRepo.save(diamondPlan12Months);
        }
    }
}

