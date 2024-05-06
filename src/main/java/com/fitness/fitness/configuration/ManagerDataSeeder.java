package com.fitness.fitness.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fitness.fitness.model.Manager;
import com.fitness.fitness.repository.ManagerRepo;

@Component
public class ManagerDataSeeder implements CommandLineRunner {

    @Autowired
    private ManagerRepo managerRepo;

    public ManagerDataSeeder(ManagerRepo managerRepo) {
        this.managerRepo = managerRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if managers already exist in the database
        if (managerRepo.count() == 0) {
            // Seed data for managers
            Manager manager1 = new Manager(1,"master@l8fitness.com", "Master123");
            Manager manager2 = new Manager(2,"master2@18fitness.com", "Master123");

            // Save managers to the database
            managerRepo.save(manager1);
            managerRepo.save(manager2);
        }
    }
}
