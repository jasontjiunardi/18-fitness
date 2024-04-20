package com.fitness.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.Review;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long>{
    List<Review> findByTrainer(Trainer trainer);
}
