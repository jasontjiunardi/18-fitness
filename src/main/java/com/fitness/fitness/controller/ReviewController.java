package com.fitness.fitness.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

        Trainer trainer = trainerService.getTrainerById(trainerId);
        List<Review> reviews = reviewService.findByTrainer(trainer);
        model.addAttribute("reviews", reviews);

        model.addAttribute("trainer", trainer);

        return "userViewReviews";
    }
 @GetMapping("/addReview")
    public String addReview(Model model, @SessionAttribute("user") User user, @RequestParam("trainerId") int trainerId) {
        List<Trainer> trainers = trainerService.getAllTrainers();
        model.addAttribute("trainers", trainers);

        User retrievedUser = userService.getUserByEmail(user.getEmail());
        model.addAttribute("retrievedUser", retrievedUser);

        Trainer trainer = trainerService.getTrainerById(trainerId);
        Review review = new Review();
        review.setTrainer(trainer);
        review.setUser(user); // Set the user from session

        model.addAttribute("review", review);
        return "addReview"; // Make sure this is the correct name of your HTML file
    }

    @PostMapping("/saveReview")
public String saveReview(@ModelAttribute("review") Review review, @SessionAttribute("user") User user, @RequestParam("trainerId") int trainerId) {
    
    Trainer trainer = trainerService.getTrainerById(trainerId);  // Make sure the trainer exists
    if (trainer == null) {
        return "redirect:/errorPage"; // Or handle this more gracefully
    }

    review.setUser(user);  // Ensure user is set from session if not already
    review.setTrainer(trainer);  // Ensure trainer is correctly associated
    review.setDate(LocalDateTime.now());  // Set the current date and time
    reviewService.saveReview(review);  // Save the review

    return "redirect:/trainerReview?trainerId=" + trainerId;
}

@PostMapping("/removeReview")
public String removeReview(@RequestParam("reviewId") int reviewId, @SessionAttribute("user") User user, Model model) {
    // Retrieve the review from the database
    Review review = reviewService.findReviewById(reviewId);

    // Check if the review exists and belongs to the logged-in user
    if (review != null && review.getUser().getUserId() == user.getUserId()) {
        int trainerId = review.getTrainer().getId();

        // Remove the review
        reviewService.deleteReview(reviewId);

        // Redirect to the trainer review page with the trainerId
        return "redirect:/trainerReview?trainerId=" + trainerId;
    } else {
        // If review doesn't exist or doesn't belong to the logged-in user, handle error
        model.addAttribute("error", "You are not authorized to remove this review.");
        return "errorPage"; 
    }
}

}