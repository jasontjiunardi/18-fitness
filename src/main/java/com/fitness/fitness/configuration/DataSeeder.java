package com.fitness.fitness.configuration;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.BenefitRepo;
import com.fitness.fitness.repository.PlanDurationPriceRepo;
import com.fitness.fitness.repository.PlanRepo;
import com.fitness.fitness.repository.TrainerRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataSeeder {

    /**
     * @param planRepo
     * @param benefitRepo
     * @param planDurationPriceRepo
     * @param trainerRepo
     * @return
     */
    @Bean
    @Transactional
    CommandLineRunner initDatabase(PlanRepo planRepo, BenefitRepo benefitRepo, PlanDurationPriceRepo planDurationPriceRepo, TrainerRepo trainerRepo) {
        return args -> {
            // Only seed data if no benefits exist
            if (planRepo.count() == 0) {
                // Define benefits
                Plan silverPlan = new Plan("Silver", "This plan offers a balanced approach to fitness, perfect for those who are getting started on their fitness journey. Enjoy access to all gym facilities and a selection of group classes.");
                Plan goldPlan = new Plan("Gold", "Elevate your fitness experience with our Gold Plan. This includes everything in the Silver Plan, plus additional benefits such as personal training sessions and unlimited class bookings.");
                Plan diamondPlan = new Plan("Diamond", "Our premium offering, the Diamond Plan, is designed for the ultimate fitness enthusiast. It encompasses all Gold Plan features, with exclusive access to VIP locker rooms, advanced health assessments, and more.");

                // Save Plans
                silverPlan = planRepo.save(silverPlan);
                goldPlan = planRepo.save(goldPlan);
                diamondPlan = planRepo.save(diamondPlan);

                // Create and save PlanDurationPrices for each plan
                planDurationPriceRepo.save(new PlanDurationPrice(silverPlan, 6, 50));
                planDurationPriceRepo.save(new PlanDurationPrice(silverPlan, 12, 90));
                
                planDurationPriceRepo.save(new PlanDurationPrice(goldPlan, 6, 80));
                planDurationPriceRepo.save(new PlanDurationPrice(goldPlan, 12, 150));
                
                planDurationPriceRepo.save(new PlanDurationPrice(diamondPlan, 6, 140));
                planDurationPriceRepo.save(new PlanDurationPrice(diamondPlan, 12, 250));

                Benefit gymBag = new Benefit("Free gym bag");
                Benefit exclusiveContent = new Benefit("Access to exclusive online content");
                Benefit groupClasses = new Benefit("Free entry to group classes");
                Benefit vipAccess = new Benefit("VIP lounge access");

                benefitRepo.save(gymBag);
                benefitRepo.save(exclusiveContent);
                benefitRepo.save(groupClasses);
                benefitRepo.save(vipAccess);

                // Fetch plans from the database and associate benefits
                planRepo.findAll().forEach(plan -> {
                    Set<Benefit> benefits = new HashSet<>();
                    switch (plan.getPlanType()) {
                        case "Silver":
                            benefits.add(gymBag);
                            benefits.add(exclusiveContent);
                            break;
                        case "Gold":
                            benefits.add(gymBag);
                            benefits.add(exclusiveContent);
                            benefits.add(groupClasses);
                            break;
                        case "Diamond":
                            benefits.add(gymBag);
                            benefits.add(exclusiveContent);
                            benefits.add(groupClasses);
                            benefits.add(vipAccess);
                            break;
                    }
                    plan.setBenefits(benefits);
                    planRepo.save(plan);
                });

                Trainer trainerRank3 = new Trainer( "Chris Bumstead", 27, "Male", "1997-01-01", "cbum@gmail.com", "trainer1.jpg", "188-888-888", 5, "2024-01-01");
                Trainer trainerRank4 = new Trainer( "Noel Deyzel", 35, "Male", "1997-02-01", "dbum@gmail.com", "trainer2.jpg", "288-888-888", 4, "2024-02-01");
                Trainer trainerRank5 = new Trainer( "Sam Sulek", 30, "Female", "1997-03-01", "ebum@gmail.com", "trainer3.jpg", "388-888-888", 3, "2024-03-01");
    
                trainerRepo.save(trainerRank3);
                trainerRepo.save(trainerRank4);
                trainerRepo.save(trainerRank5);


                planRepo.findAll().forEach(plan -> {
                    Set<Trainer> trainers = new HashSet<>();
                    switch (plan.getPlanType()) {
                        case "Silver":
                            trainers.add(trainerRank3);
                            break;
                        case "Gold":
                            trainers.add(trainerRank3);
                            trainers.add(trainerRank4);
                            break;
                        case "Diamond":
                            trainers.add(trainerRank3);
                            trainers.add(trainerRank4);
                            trainers.add(trainerRank5);
                            break;
                    }
                    plan.setTrainers(trainers);
                    planRepo.save(plan);
            });
        };
    };
}
}
