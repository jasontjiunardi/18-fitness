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
            return "loginsuccess";}
        return "sign";
    }

}
