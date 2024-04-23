package com.fitness.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Manager;
import com.fitness.fitness.repository.ManagerRepo;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepo managerRepo;

    public ManagerService(ManagerRepo managerRepo){
        this.managerRepo = managerRepo;
    }

    public boolean managerLogin(Manager manager) {
        Manager m = managerRepo.findByEmail(manager.getEmail());
        if (m != null && m.getPassword().equals(manager.getPassword())) {
            return true;
        }
        return false;
    }

    public Manager manageraddAppointment(Manager manager) {
        return managerRepo.save(manager);
    }
}
