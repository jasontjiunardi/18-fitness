package com.fitness.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.service.TrainerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class TrainerController {
    @Autowired
    private TrainerRepo trainerrepo;
    private TrainerService trainerService;

    @GetMapping("/view_trainers")
    public String viewTrainers(Model model) {
        List<Trainer> trainers = trainerrepo.findAll();
        model.addAttribute("trainers", trainers);
        return "trainers";
    }
    
    
    
    @GetMapping("/trainer_profile/{id}")
    public String TrainerProfile(@PathVariable int id, Model model) {
        Trainer trainer = trainerrepo.findById(id).orElse(null);
        model.addAttribute("trainer", trainer);
        return "trainerprofile";
        
        
    }
    
}
