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
