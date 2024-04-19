package com.fitness.fitness.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.model.Trainer;


@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;

    public List<Trainer> getAll(){
        return trainerRepo.findAll();
    }
    public Optional<Trainer> findById(int id) {
        return trainerRepo.findById(id);
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
    }

    // You can add more methods here depending on your business logic
