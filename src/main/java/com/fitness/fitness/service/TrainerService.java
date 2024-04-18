package com.fitness.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.model.Trainer;
import java.util.List;

@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;

    // You can add methods to perform operations using trainerRepo

    public List<Trainer> findAllByRank(int rank) {
        return trainerRepo.findAllByRank(rank);
    }

    public List<Trainer> getAllTrainers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTrainers'");
    }

    // You can add more methods here depending on your business logic
}
