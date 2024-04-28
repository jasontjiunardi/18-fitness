/*package com.fitness.fitness.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fitness.fitness.model.Review;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.ReviewRepo;
import com.fitness.fitness.repository.TrainerRepo;

@Component
public class ReviewDataSeeder implements CommandLineRunner {

    private final TrainerRepo trainerRepo;
    private final ReviewRepo reviewRepo;

    public ReviewDataSeeder(TrainerRepo trainerRepo, ReviewRepo reviewRepo) {
        this.trainerRepo = trainerRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (reviewRepo.count() == 0) {
            seedReviews();
        }
    }

    private void seedReviews() {
        Trainer trainer1 = trainerRepo.findById(1).orElse(null);
        Trainer trainer2 = trainerRepo.findById(2).orElse(null);

        if (trainer1 != null && trainer2 != null) {
            List<Review> reviews = new ArrayList<>();

            // Reviews for Trainer 1
            reviews.add(new Review(null, "JohnDoe", 5, "Great trainer!", new Date()));
            reviews.add(new Review(null, "JaneSmith", 4, "Enjoyed the sessions.", new Date()));

            // Reviews for Trainer 2
            reviews.add(new Review(null, "Alice", 3, "Average experience.", new Date()));
            reviews.add(new Review(null, "Bob", 5, "Highly recommend!", new Date()));

            // Set trainers for the reviews
            for (int i = 0; i < reviews.size(); i++) {
                if (i % 2 == 0) {
                    reviews.get(i).setTrainer(trainer1); // Assign reviews to trainer 1
                } else {
                    reviews.get(i).setTrainer(trainer2); // Assign reviews to trainer 2
                }
            }

            // Save reviews to the database
            reviewRepo.saveAll(reviews);
        }
    }
}*/
