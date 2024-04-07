package com.fitness.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fitness.fitness.model.Benefit;

@Repository
public interface BenefitRepo extends JpaRepository<Benefit, Integer> {
}