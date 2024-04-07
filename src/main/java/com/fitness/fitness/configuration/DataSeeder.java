package com.fitness.fitness.configuration;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.repository.BenefitRepo;
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
    CommandLineRunner initDatabase(PlanRepo planRepo, BenefitRepo benefitRepo) {
        return args -> {
            // Only seed data if no benefits exist
            if (benefitRepo.count() == 0) {
                // Define benefits
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
