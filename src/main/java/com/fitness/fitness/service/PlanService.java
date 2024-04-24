package com.fitness.fitness.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.PlanDurationPriceRepo;
import com.fitness.fitness.repository.PlanRepo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanService {
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private PlanDurationPriceRepo planDurationPriceRepo;

    public Plan findByPlanType(String planType) {
        return planRepo.findByPlanType(planType);
    }

    public Map<String, Double> getStartingPricesForAllPlanTypes() {
        List<PlanDurationPrice> plans = planDurationPriceRepo.findAll();
        Map<String, List<PlanDurationPrice>> plansByType = plans.stream()
                .collect(Collectors.groupingBy(plan -> plan.getPlan().getPlanType()));

        Map<String, Double> startingPrices = new HashMap<>();
        plansByType.forEach((planType, planList) -> {
            double startingPrice = planList.stream()
                    .mapToDouble(PlanDurationPrice::getPlanPrice)
                    .min()
                    .orElse(0.0);
            startingPrices.put(planType, startingPrice);
        });
        return startingPrices;
    }
    
    public LinkedHashMap<String,Double> getSortedPrices(){
    LinkedHashMap<String, Double> sortedStartingPrices = getStartingPricesForAllPlanTypes().entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (oldValue, newValue) -> oldValue,
                LinkedHashMap::new));
                return sortedStartingPrices;
    }
    public Map<String, List<Benefit>> findBenefitsByPlanType(String planType) {
        Map<String, List<Benefit>> planBenefits = new HashMap<>();
        
        Plan plan = planRepo.findByPlanType(planType);
        if (plan != null) {
            List<Benefit> benefits = plan.getBenefits().stream()
                .sorted(Comparator.comparing(Benefit::getDescription))
                .collect(Collectors.toList());
            planBenefits.put(planType, benefits);
        }
        
        return planBenefits;
    }
    public Map<String, List<Benefit>> sortedBenefits(){
        List<String> sortedPlanTypes = new ArrayList<>(getSortedPrices().keySet());
        Map<String, List<Benefit>> planBenefits = new HashMap<>();
        sortedPlanTypes.forEach(planType -> {
            List<Benefit> benefits = findBenefitsByPlanType(planType).get(planType);
            planBenefits.put(planType, benefits);
        });
        return planBenefits;
    }
    public Map<String, Map<Integer,Double>> getStartingPricesAndDurationForAllPlanTypes() {
        List<PlanDurationPrice> plans = planDurationPriceRepo.findAll();
        Map<String, List<PlanDurationPrice>> plansByType = plans.stream()
                .collect(Collectors.groupingBy(plan -> plan.getPlan().getPlanType()));

        Map<String, Map<Integer,Double>> startingDurationPrices = new HashMap<>();
        
        plansByType.forEach((planType, planList) -> {
            Map<Integer,Double> durationPrice = new HashMap<>();
            double startingPrice = planList.stream()
                    .mapToDouble(PlanDurationPrice::getPlanPrice)
                    .min()
                    .orElse(0.0);
            Integer startingDuration = planList.stream().mapToInt(PlanDurationPrice::getPlanDuration).min().orElse(0);
            durationPrice.put(startingDuration, startingPrice);
            startingDurationPrices.put(planType, durationPrice);
        });
        return startingDurationPrices;
    }
    public Double specificPlanStartDurationStartPrice(String planType, User user) {
        Map<String, Map<Integer, Double>> startingPrice = getStartingPricesAndDurationForAllPlanTypes();
        Double price = 0.0;
        Double priceComparator = 0.0;
        if(startingPrice.containsKey(user.getStatus())){
            Map<Integer, Double> startingDurationPrices = startingPrice.get(user.getStatus());
            if (user != null && user.getActiveDate() != null) {
                LocalDate activeDate = user.getActiveDate();
                LocalDate today = LocalDate.now();
                if (!activeDate.isBefore(today)) {
                    Double remainingMonths = (double) ChronoUnit.MONTHS.between(today, activeDate);
                    if (!startingDurationPrices.isEmpty()) {
                        Integer duration = startingDurationPrices.keySet().iterator().next();
                        if (duration > 0) { 
                            double durationPrice = startingDurationPrices.get(duration);
                            priceComparator = remainingMonths / duration * durationPrice;
                            priceComparator = (double) (Math.round(priceComparator));
                        }
                    }
                }
            } 
        }
        if (startingPrice.containsKey(planType)) {
            Map<Integer, Double> startingDurationPrices = startingPrice.get(planType);
            if (user != null && user.getActiveDate() != null) {
                LocalDate activeDate = user.getActiveDate();
                LocalDate today = LocalDate.now();
                if (!activeDate.isBefore(today)) {
                    Double remainingMonths = (double) ChronoUnit.MONTHS.between(today, activeDate);
                    if (!startingDurationPrices.isEmpty()) {
                        Integer duration = startingDurationPrices.keySet().iterator().next();
                        if (duration > 0) { 
                            double durationPrice = startingDurationPrices.get(duration);
                            price = remainingMonths / duration * durationPrice;
                            price = (double) (Math.round(price));
                            if(price<priceComparator){
                                price = 0.0;
                            }
                        }
                    }
                }
            }
        }
    
        return price;
    }

}
    
    
    

