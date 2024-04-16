package com.fitness.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fitness.fitness.model.User;
import com.fitness.fitness.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class UserController {
    @Autowired
    private UserService userService;

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
}
