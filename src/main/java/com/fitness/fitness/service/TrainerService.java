package com.fitness.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.fitness.fitness.repository.TrainerRepo;

public class TrainerService {
    @Autowired
    private TrainerRepo trainerRepo;

}
