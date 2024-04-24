package com.fitness.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Trainer;

@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Integer>{
    
    public List<Trainer> findAllByRank(int rank);
    List<Trainer> findAll();
    Trainer findByName(String name);
    List<Trainer> findByRank(int rank);
    @Query("SELECT t FROM Trainer t JOIN t.plans p WHERE p.id = :planId")
    List<Trainer> findTrainersByPlanId(int planId);

}

    
