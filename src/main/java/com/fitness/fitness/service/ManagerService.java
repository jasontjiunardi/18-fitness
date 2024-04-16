package com.fitness.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Manager;
import com.fitness.fitness.repository.ManagerRepo;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepo ManagerRepo;
    public Manager manageraddAppointment(Manager manager) {
        return ManagerRepo.save(manager);
    }
}
