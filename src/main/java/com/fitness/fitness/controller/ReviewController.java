/*package com.fitness.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fitness.fitness.model.Review;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.service.ReviewService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;

@Controller
public class ReviewController {
    

    private final UserService userService;
    private final TrainerService trainerService;
    private final ReviewService reviewService;

        @Autowired
        public ReviewController(UserService userService, TrainerService trainerService, ReviewService reviewService){
            this.userService = userService;
            this.trainerService = trainerService;
            this.reviewService = reviewService;
        }

    @GetMapping("/trainerReview")
    public String viewReviews(Model model, @SessionAttribute("user") User user, @RequestParam("trainerId") int trainerId) {
    List<Trainer> trainers = trainerService.getAllTrainers();
    model.addAttribute("trainers", trainers);

    
    User retrievedUser = userService.getUserByEmail(user.getEmail());
    model.addAttribute("retrievedUser", retrievedUser); // Add retrievedUser to the model as an attribute
    int userId = retrievedUser.getUserId(); // ID from user email    
    
    List<Review> reviews = reviewService.getAllReviews();
    model.addAttribute( "reviews", reviews);

    return "Trainer review";
}
}*/