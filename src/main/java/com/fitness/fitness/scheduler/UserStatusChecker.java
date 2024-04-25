package com.fitness.fitness.scheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.UserRepo;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserStatusChecker {

    private final UserRepo userRepo;

    public UserStatusChecker(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    @PostConstruct
    public void init() {
        updateInactiveUsers(); // Call during startup
    }

    @Scheduled(cron = "0 0 0 * * ?") //  Midnight maintenance for user statuses
    @Transactional
    public void updateInactiveUsers() {
        LocalDate today = LocalDate.now();
        List<User> users = userRepo.findAll();
        for (User user : users) {
            if (user.getActiveDate()!=null && user.getActiveDate().isBefore(today)) {
                user.setStatus("Inactive");
                user.setActiveDate(null);
                userRepo.save(user);
            }
        }
    }
}