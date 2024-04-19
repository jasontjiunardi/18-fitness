package com.fitness.fitness.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.TrainerRepo;
@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;

    public Trainer findById(int id) {
        return trainerRepo.findById(id).orElse(null);
    }


    // You can add methods to perform operations using trainerRepo

    public List<Trainer> findAllByRank(int rank) {
        return trainerRepo.findAllByRank(rank);
    }

    public List<Trainer> getAllTrainers() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'getAllTrainers'");
            return trainerRepo.findAll();
        }

    public Trainer getTrainerById(Integer id) {
        Optional<Trainer> optionalTrainer = trainerRepo.findById(id);
        return optionalTrainer.orElse(null);
    }

    public Trainer findTrainerByName(String name) {
        return trainerRepo.findByName(name);
    }

}

    // You can add more methods here depending on your business logic
