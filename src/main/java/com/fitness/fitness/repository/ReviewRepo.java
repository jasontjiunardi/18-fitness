package com.fitness.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Review;
import com.fitness.fitness.model.Trainer;

import jakarta.transaction.Transactional;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findByTrainer(Trainer trainer);
    List<Review> findByUserUserId(int userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Review r  WHERE r.user.id = :id")
    void deleteReviewByUserId(@Param("id") int id);
}