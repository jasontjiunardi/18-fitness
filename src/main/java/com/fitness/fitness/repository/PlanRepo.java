package com.fitness.fitness.repository;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Plan;


@Repository
public interface PlanRepo extends JpaRepository<Plan,Integer>{
    List <Plan> findByPlanType(String planType);


}
