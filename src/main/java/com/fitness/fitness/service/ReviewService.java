package com.fitness.fitness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.Review;
import com.fitness.fitness.repository.ReviewRepo;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepo reviewRepo;

    public List<Review> findByTrainer(Trainer trainer) {
        return reviewRepo.findByTrainer(trainer);
    }

    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    public Review saveReview(Review review) {
        return reviewRepo.save(review);
    }

    public Review findReviewById(int id) {
        return reviewRepo.findById(id).orElse(null);
    }

    public void deleteReview(int id) {
        reviewRepo.deleteById(id);
    }

    public void deleteAllReviewsByUserId(int id) {
        List<Review> reviews = reviewRepo.findByUserUserId(id);
        for (Review review : reviews) {
            reviewRepo.delete(review);
        }
    }
}