package com.fitness.fitness.controller;

import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.service.FitnessClassService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class FitnessClassController {
    
    @Autowired
    private final FitnessClassService fitnessClassService;

    public FitnessClassController(FitnessClassService fitnessClassService) {
        this.fitnessClassService = fitnessClassService;
    }

    @GetMapping("/showFitnessClasses")
    public String showAllFitnessClasses(Model model) {
        List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        model.addAttribute("allClasses", allClasses);
        return "fitnessclasses"; // Assuming "allfitnessclasses" is the name of your HTML template
    }
}
    