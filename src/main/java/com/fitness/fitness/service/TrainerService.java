package com.fitness.fitness.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.Review;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.repository.PlanRepo;
import com.fitness.fitness.repository.ReviewRepo;
import com.fitness.fitness.repository.TrainerRepo;

import jakarta.transaction.Transactional;

@Service
public class TrainerService {
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private ReviewRepo reviewRepo;

    public Trainer findById(int id) {
        return trainerRepo.findById(id).orElse(null);
    }

    // You can add methods to perform operations using trainerRepo

    public List<Trainer> findAllByRank(int rank) {
        return trainerRepo.findAllByRank(rank);
    }

    public List<Trainer> getAllTrainers() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAllTrainers'");
        return trainerRepo.findAll();
    }

    public Trainer getTrainerById(Integer id) {
        Optional<Trainer> optionalTrainer = trainerRepo.findById(id);
        return optionalTrainer.orElse(null);
    }

    public Trainer findTrainerByName(String name) {
        return trainerRepo.findByName(name);
    }

    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepo.save(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerRepo.save(trainer);
    }

    @Transactional
    public void removeTrainer(int id) throws Exception {
        Trainer trainer = trainerRepo.findById(id).orElseThrow(() -> new Exception("Trainer not found!"));

        // Remove all reviews associated with this trainer
        List<Review> reviews = reviewRepo.findByTrainer(trainer);
        if (!reviews.isEmpty()) {
            reviewRepo.deleteAll(reviews); // Delete reviews directly
        }

        // Remove all appointments associated with this trainer
        List<Appointment> appointments = appointmentRepo.findByTrainer(trainer);
        if (!appointments.isEmpty()) {
            appointmentRepo.deleteAll(appointments); // Delete appointments directly
        }

        // Remove trainer from any plans
        List<Plan> plans = planRepo.findAll();
        plans.forEach(plan -> {
            if (plan.getTrainers().contains(trainer)) {
                plan.getTrainers().remove(trainer);
                planRepo.save(plan); // Save the updated plan
            }
        });

        // Finally, delete the trainer
        trainerRepo.delete(trainer);
    }

    public Trainer getTrainerByName(String trainerName) {
        return trainerRepo.findByName(trainerName);
    }

    public List<Trainer> getTrainersByPlanId(int planId) {
        return trainerRepo.findTrainersByPlanId(planId);
    }
}

// public void updateEligiblePlans(Trainer trainer) {
// Set<Plan> eligiblePlans = new HashSet<>();
// switch (trainer.getRank()) {
// case 3:
// eligiblePlans.addAll(planRepo.findByPlanTypeIn(Arrays.asList("silver",
// "gold", "diamond")));
// break;
// case 4:
// eligiblePlans.addAll(planRepo.findByPlanTypeIn(Arrays.asList("silver",
// "gold")));
// break;
// case 5:
// eligiblePlans.addAll(planRepo.findByPlanTypeIn(Collections.singletonList("gold")));
// break;
// default:
// break; // No eligible plans for other ranks
// }
// trainer.setEligiblePlans(eligiblePlans);
// trainerRepo.save(trainer);
// }

// You can add more methods here depending on your business logic
