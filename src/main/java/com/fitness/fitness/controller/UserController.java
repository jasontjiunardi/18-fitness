package com.fitness.fitness.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.fitness.model.User;
import com.fitness.fitness.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/first_page")
    public String firstPage(Model model){

        return "firstpage";
    }
    @GetMapping("/register_user")
    public String getNewUserPage(Model model){
        model.addAttribute("user", new User());
        return "registeruserform";
    }

    @PostMapping("/register_user")
    public String registerUser(@ModelAttribute User user, HttpSession session) {
        if (userService.verifyUser(user)) {
            if(userService.emailValid(user.getEmail()) && userService.passwordValid(user.getPassword())){
                userService.saveUser(user); 
                session.setAttribute("user", user);
                return "home";
            }
        }
        return "registeruserform";
    }
    
    @GetMapping("/user_signin")
    public String showLoginForm(Model model) {
        User existingUser = new User();
        model.addAttribute("user", existingUser ) ;
        return "sign";
    }

    @PostMapping("/user_signin")
    public String login(@ModelAttribute User user, HttpSession session) {
        if (userService.userLogin(user)) {
            session.setAttribute("user", user);
            return "home";
        }
        return "login_fail";
    }

    @GetMapping("/forget_password")
    public String userForgetPassword(Model model){
        User existingUser = new User();
        model.addAttribute("user", existingUser);
        return "forgetPassword";
    }
    
 
    @PostMapping("/forget_password")
    public String userEnterCode(@ModelAttribute User user, Model model){
        if (userService.userRecoveryCode(user) && userService.newPassword(user)) {
            userService.saveUser(user);
            return "sign";
        }
        
        model.addAttribute("error", "Invalid recovery code or password");
        return "forgetpassword";
    }
    
    @GetMapping("/profile")
    public String userProfile(Model model, @SessionAttribute("user") User user) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        if (retrievedUser != null) {
            model.addAttribute("user", retrievedUser);
        return "profile";
            } else {
            model.addAttribute("error", "User not found");
        return "profile";
        }
    }

    @GetMapping("/edit_profile")
    public String userEditInformation(Model model, @SessionAttribute("user") User user) {
        String userEmail = user.getEmail();
        User existingUser = userService.getUserByEmail(userEmail);
        model.addAttribute("user", existingUser);
        return "editProfile";
    }

    @PostMapping("/edit_profile")
    public String userEditInformation(@ModelAttribute User user, Model model) {
        userService.saveUser(user);
        return "editProfile"; // Redirect to the profile page after saving
}

    @GetMapping("/add_credit_card")
    public String addCreditCard(Model model,User user) {
        User u = userService.getUserByEmail(user.getEmail());
        model.addAttribute("user", u);
        return "profile";
    }
    
    @PostMapping("/add_credit_card")
    public String userAddCreditCard(@ModelAttribute User user, Model model) {
        String cardNumber = user.getCardNumber();
        userService.setCreditCardNumber(user.getEmail(), cardNumber);
        return "profile"; 
    }
    @GetMapping("/home_page")
    public String getHomePage(Model model, @SessionAttribute("user") User user) {
        if (user != null) {
            model.addAttribute("user", user);
            return "home";
        } else {
            return "login";
        }
}
    @GetMapping("/upload_profile_picture")
    public String showProfilePictureUploadForm(Model model, @SessionAttribute("user") User user) {
    model.addAttribute("user", user);
    return "profilePicture";
}

    @PostMapping("/upload_profile_picture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, @SessionAttribute("user") User user) {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = Paths.get("uploads", fileName).toString();
                Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                
                String profilePictureUrl = "/uploads/" + fileName; // Adjust this URL as needed
                
                userService.setProfilePicture(user.getEmail(), profilePictureUrl);
                
                return "redirect:/profile";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "profile";
    }

    @PostMapping("/pause_account")
    public String pauseAccount(Model model, @SessionAttribute("user") User user) {
        // Retrieve user from database to ensure the data is up-to-date
        User existingUser = userService.getUserByEmail(user.getEmail());
        
        // Check if user status is active
        if ("active".equals(existingUser.getStatus())) {
            // Set pause start date
            existingUser.setPauseStartDate(LocalDate.now());
            
            // Set pause end date (30 days from now)
            LocalDate pauseEndDate = LocalDate.now().plusDays(30); // 30 days from now
            existingUser.setPauseEndDate(pauseEndDate);
            
            // Update user status to paused
            existingUser.setStatus("paused");

            // Save updated user data to database
            userService.saveUser(existingUser);
            
            // Redirect to profile page
            return "redirect:/profile";
        } else if ("paused".equals(existingUser.getStatus())) {
            // Check if pause end date has passed
            LocalDate currentDate = LocalDate.now();
            LocalDate pauseEndDate = existingUser.getPauseEndDate();
            if (currentDate.isAfter(pauseEndDate)) {
                // Update user status to active
                existingUser.setStatus("active");
                
                // Save updated user data to database
                userService.saveUser(existingUser);
                
                // Redirect to profile page with notification
                return "redirect:/profile?notification=Your account has been reactivated.";
            } else {
                // User is already paused, redirect to profile with a notification
                return "redirect:/profile?notification=Your account is already paused.";
            }
        } else {
            // User is inactive, redirect to profile with a notification
            return "redirect:/profile?notification=You must have an active plan to pause your account.";
        }
    }


    @PostMapping("/unpause_account")
    public String unpauseAccount(Model model, @SessionAttribute("user") User user) {
        // Retrieve user from database to ensure the data is up-to-date
        User existingUser = userService.getUserByEmail(user.getEmail());

        // Check if the user status is "paused"
        if ("paused".equals(existingUser.getStatus())) {
            // Hitung durasi pembekuan
            LocalDate pauseStartDate = existingUser.getPauseStartDate();
            LocalDate today = LocalDate.now();
            Duration duration = Duration.between(pauseStartDate.atStartOfDay(), today.atStartOfDay());
            long daysPaused = duration.toDays();

            // Simpan jumlah hari pembekuan dalam variabel atau di objek User
            existingUser.setDaysPaused(daysPaused);

            // Set pause start date to null
            existingUser.setPauseStartDate(null);
            
            // Set pause end date to null
            existingUser.setPauseEndDate(null);
            
            // Update user status to "active"
            existingUser.setStatus("active");
            
            // Save updated user data to database
            userService.saveUser(existingUser);
        }
        
        // Redirect to profile page
        return "redirect:/profile";
    }


  }
