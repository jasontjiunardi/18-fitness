package com.fitness.fitness.configuration;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.repository.BenefitRepo;
import com.fitness.fitness.repository.PlanDurationPriceRepo;
import com.fitness.fitness.repository.PlanRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(PlanRepo planRepo, BenefitRepo benefitRepo, PlanDurationPriceRepo planDurationPriceRepo) {
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
            }
        };
    }
}
