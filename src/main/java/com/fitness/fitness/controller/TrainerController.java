package com.fitness.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.model.Review;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;
import com.fitness.fitness.service.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @GetMapping("/trainers/view")
    public ModelAndView view(Model model) {
        return new ModelAndView("trainers", "trainers", trainerService.getAllTrainers());
    }

    @GetMapping("/view_trainers")
    public String viewTrainers(@RequestParam(value = "rank", required = false) Integer rank, Model model, HttpSession session) {
         User user = (User) session.getAttribute("user");
         if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            model.addAttribute("user", existingUser);
        }
        if (rank == null) {
            List<Trainer> trainers = trainerService.getAllTrainers();
            model.addAttribute("trainers", trainers);
        } else {
            List<Trainer> filteredTrainers = trainerService.findAllByRank(rank.intValue());
            model.addAttribute("trainers", filteredTrainers);
        }
        return "trainers";
    }

    @GetMapping("/trainer_profile")
    public String trainerProfile(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Trainer trainer = trainerService.findById(id);
        List<Review> reviews = reviewService.findByTrainer(trainer); // Get reviews for the trainer
        model.addAttribute("trainer", trainer);
        model.addAttribute("reviews", reviews); 
        return "trainerprofile";
    }


    
    // @GetMapping("book_appointment")
    // public String getMethodName(@RequestParam (value = "trainer.id", required=false)Integer trainerId, Model model) {
    //     return new String();
    // }
    
    
}
