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

    public List<Trainer> getAll(){
        return trainerRepo.findAll();
    }
    public Optional<Trainer> findById(int id) {
        return trainerRepo.findById(id);
    }
    public List<Trainer> findAllByRank(int rank) {
        // TODO Auto-generated method stub
        return trainerRepo.findAllByRank(rank);
    }
}
