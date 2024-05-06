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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.FileUploadUtil;
import com.fitness.fitness.model.PauseInfo;
// import com.fitness.FileUploadUtil;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.UserRepo;
import com.fitness.fitness.service.UserService;


import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@SessionAttributes("user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
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
    public String registerUser( HttpSession session, @ModelAttribute User user) {
        if (userService.verifyUser(user)) {
            if (userService.emailValid(user.getEmail()) && userService.passwordValid(user.getPassword())) {
                int recoveryCode = 1000 + (int) (Math.random() * 9000);
                user.setRecoveryCode(recoveryCode);
                userService.saveUser(user);
                session.setAttribute("user", user);
                return "redirect:/show_recovery_code";
            }
        }
        return "signup_fail";
    }

    @GetMapping("/show_recovery_code")
    public String showRecoveryCode(@SessionAttribute("user") User user, Model model) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        Integer recoveryCode = retrievedUser.getRecoveryCode();
            model.addAttribute("recoveryCode", recoveryCode);
            return "recoverycode";
    }

    @PostMapping("/user_signin")
    public String login(@ModelAttribute User user, HttpSession session) {
        if (userService.userLogin(user)) {
            session.setAttribute("user", user);
            return "redirect:/home_page";
        }
        return "login_fail";
    }

    @GetMapping("/user_signin")
    public String showLoginForm(Model model) {
        User existingUser = new User();
        model.addAttribute("user", existingUser) ;
        return "sign";
    }

    @GetMapping("/forget_password")
    public String userForgetPassword(Model model, User user){ // existinguser masuk ke model dgn nama "user"
        return "forgetPassword";
    }
    
    @PostMapping("/forget_password")
    public String processPasswordReset(Model model, @ModelAttribute User user, @RequestParam("recoveryCode") int recoveryCode, @RequestParam("newPassword") String newPassword) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        if (retrievedUser != null && retrievedUser.getRecoveryCode() == recoveryCode) {
            if (!userService.passwordValid(newPassword)) {
                model.addAttribute("error", "Your new password does not meet the required criteria.");
                return "forgetPassword";
            }
            userService.updatePasswordByEmail(user.getEmail(), newPassword);
            return "sign"; // Redirect to login page on successful password reset
        } else {
            model.addAttribute("error", "Invalid recovery code.");
            return "forgetPassword";
        }
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @SessionAttribute("user") User user) { // user in User user hold attribute from session attribute "user" atau itu masuk ke User user
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
        User existingUser = userService.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            model.addAttribute("user", existingUser);
        } else {
            return "error";
        }
        return "editProfile";
    }

    @PostMapping("/edit_profile")
    public String userEditInformation(@ModelAttribute User user, Model model) {
        userService.saveUser(user);
        return "profile"; 
    }

    @PostMapping("updateProfile")
    public String userEditInformation( @RequestParam("image") MultipartFile multipartFile,@ModelAttribute User user, Model model) throws IOException{
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            String upload = "src/main/resources/static/images/"; // Adjust this URL as needed
            FileUploadUtil.saveFile(upload, fileName, multipartFile);

        } else {
            if (user.getPhoto().isEmpty()) {
                user.setPhoto("wechat_icon.jpg");
                userService.saveUser(user);
            }
        }
        userService.saveUser(user);
        return "profile";
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
            User u = userService.getUserByEmail(user.getEmail());
            model.addAttribute("user", u);
            return "home";
        } else {
            return "login";
        }
    }

    @GetMapping("/choose_profile_picture")
    public String chooseProfilePicture(Model model) {
        model.addAttribute("avatar2", "avatar2.jpg");
        model.addAttribute("avatar3", "avatar3.jpg");
        return "profilePicture";
    }
    
    @PostMapping("/save_profile_picture")
    public String saveProfilePicture(@RequestParam("avatar") String avatar, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setPhoto(avatar);
            userService.saveUserProfile(user);
            session.setAttribute("user", user);
        }
        return "redirect:/edit_profile";
    }
    
    @PostMapping("/remove_profile_picture")
    public String removeProfilePicture(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setPhoto("avatar1.jpg");
            userService.saveUserProfile(user);
            session.setAttribute("user", user);
        }
        return "redirect:/edit_profile";
    }    
    
    @GetMapping("/reset_password")
        public String showResetPasswordForm(Model model, HttpSession session) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "/user_signin";
            }
            model.addAttribute("user", user);
            return "resetPassword";
        }
        
@PostMapping("/reset_password")
        public String resetPassword(@RequestParam("currentPassword") String password,
                                    @RequestParam("newPassword") String newPassword,
                                    @RequestParam("confirmPassword") String confirmPassword,
                                    HttpSession session, Model model) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/user_signin";
            }
        
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "New password and confirmation do not match.");
                return "resetPassword";
            }
            if (password.equals(newPassword)) {
                model.addAttribute("error", "New password cannot be the same as the current password.");
                return "resetPassword";
            }
        
            if (userService.resetPassword(user.getEmail(), password, newPassword)) {
                session.invalidate(); 
                return "redirect:/user_signin";
            } else {
                model.addAttribute("error", "Password reset failed. Please check your current password.");
                return "resetPassword";
            }
        }
// }
//     @GetMapping("/upload_profile_picture")
//     public String showProfilePictureUploadForm(Model model, @SessionAttribute("user") User user) {
//     model.addAttribute("user", user);
//     return "profilePicture";
// }

@PostMapping("/pause_account")
public String pauseAccount(Model model, @SessionAttribute("user") User user) {
    User existingUser = userService.getUserByEmail(user.getEmail());

    if ("paused".equals(existingUser.getStatus())) {
        // User already paused, no need to create new PauseInfo
        return "redirect:/profile?notification=Your account is already paused.";
    }

    // Check if user has an existing PauseInfo entry
    PauseInfo existingPauseInfo = existingUser.getPauseInfo();
    if (existingPauseInfo != null) {
        // Update existing PauseInfo
        existingPauseInfo.setPauseStartDate(LocalDate.now());
        existingPauseInfo.setPauseEndDate(LocalDate.now().plusDays(30));
        existingUser.setStatus("paused");
        userService.saveUser(existingUser);
    } else {
        // Create new PauseInfo
        PauseInfo pauseInfo = new PauseInfo();
        pauseInfo.setPauseStartDate(LocalDate.now());
        pauseInfo.setPauseEndDate(LocalDate.now().plusDays(30));
        pauseInfo.setUser(existingUser);
        pauseInfo.setStatus(existingUser.getStatus());
        existingUser.setPauseInfo(pauseInfo);
        existingUser.setStatus("paused");
        userService.saveUser(existingUser);
    }

    return "redirect:/profile";
}


    @PostMapping("/unpause_account")
    public String unpauseAccount(Model model, @SessionAttribute("user") User user) {
        User existingUser = userService.getUserByEmail(user.getEmail());

        if ("paused".equals(existingUser.getStatus())) {
            PauseInfo pauseInfo = existingUser.getPauseInfo();
            LocalDate pauseStartDate = pauseInfo.getPauseStartDate();
            LocalDate today = LocalDate.now();
            long daysPaused = Duration.between(pauseStartDate.atStartOfDay(), today.atStartOfDay()).toDays();
            pauseInfo.setDaysPaused(daysPaused);
            pauseInfo.setPauseStartDate(null);
            pauseInfo.setPauseEndDate(null);
            existingUser.setStatus(existingUser.getPauseInfo().getStatus());
            userService.saveUser(existingUser);
        }

        return "redirect:/profile";
    }
  }

