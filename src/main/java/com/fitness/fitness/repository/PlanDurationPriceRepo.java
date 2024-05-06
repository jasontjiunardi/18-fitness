package com.fitness.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fitness.fitness.model.PlanDurationPrice;

public interface PlanDurationPriceRepo extends JpaRepository<PlanDurationPrice, Integer> {
    @Query("SELECT pdp.plan.planType, MIN(pdp.planPrice) FROM PlanDurationPrice pdp GROUP BY pdp.plan.planType")
    List<Object[]> findStartingPricesForAllPlanTypes();
}
