package com.fitness.fitness.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.TrainerRepo;
=======
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.model.Trainer;
import java.util.List;

>>>>>>> 56a0767345033be674f69e8187e16f3e4d3bcebb
@Service
public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;

<<<<<<< HEAD
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
=======
    // You can add methods to perform operations using trainerRepo

    public List<Trainer> findAllByRank(int rank) {
        return trainerRepo.findAllByRank(rank);
    }

    // You can add more methods here depending on your business logic
>>>>>>> 56a0767345033be674f69e8187e16f3e4d3bcebb
}
