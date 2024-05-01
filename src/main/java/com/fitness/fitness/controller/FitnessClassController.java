package com.fitness.fitness.controller;

import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.model.User;
import com.fitness.fitness.service.FitnessClassService;
import com.fitness.fitness.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;




@Controller
public class FitnessClassController {
    
    @Autowired
    private final FitnessClassService fitnessClassService;
    private final UserService userService;

    public FitnessClassController(FitnessClassService fitnessClassService, UserService userService) {
        this.fitnessClassService = fitnessClassService;
        this.userService = userService;
    }
   

    @GetMapping("/showFitnessClasses")
    public String showAllFitnessClasses(Model model, @SessionAttribute("user") User user) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        model.addAttribute("retrievedUser", retrievedUser);
        List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        model.addAttribute("allClasses", allClasses);
        return "fitnessclasses"; // Assuming "allfitnessclasses" is the name of your HTML template
    }
}
    