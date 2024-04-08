package com.fitness.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fitness.fitness.model.User;
import com.fitness.fitness.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    
    @GetMapping("/user_signin")
    public String showLoginForm(Model model) {
        User existingUser = new User();
        model.addAttribute("user", existingUser ) ;
        return "sign";
    }

    @PostMapping("/user_signin")
    public String userLoginPage (@ModelAttribute User user){
        if(userService.userLogin(user)){
            return "home";}
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
            // Redirect to the sign-in page after successful password update
            return "sign";
        }
        
        model.addAttribute("error", "Invalid recovery code or password");
        return "forgetpassword";
    }
    
    @GetMapping("/profile")
    public String userProfile(Model model, User user) {
    // Retrieve user information based on the provided user object
    User retrievedUser = userService.getUserByEmail(user.getEmail());

    if (retrievedUser != null) {
        // If the user exists, add user information to the model
        model.addAttribute("user", retrievedUser);
        return "profile";
    } else {
        // If the user does not exist, handle it accordingly (e.g., show an error message)
        model.addAttribute("error", "User not found");
        return "profile";
    }
    }
    
    @GetMapping("/register_user_form")
    public String getNewUserPage(Model model){
        model.addAttribute("user", new User());
        return "registeruserform";
    }

    @PostMapping("/register_user")
    public String registerUser(@ModelAttribute User user){
        userService.saveUser(user);

        return "home";
    }

}




