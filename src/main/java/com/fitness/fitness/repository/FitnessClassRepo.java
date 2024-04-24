package com.fitness.fitness.repository;

import com.fitness.fitness.model.FitnessClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessClassRepo extends JpaRepository<FitnessClass, Integer> {
    public FitnessClass findById(int id);
    public FitnessClass findByClassName(String className);
}
