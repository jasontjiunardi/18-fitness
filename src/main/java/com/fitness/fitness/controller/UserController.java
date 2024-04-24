package com.fitness.fitness.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    // @PostMapping("/register_user")
    // public String registerUser(@ModelAttribute User user, HttpSession session) {
    //     if (userService.verifyUser(user)) {
    //         if(userService.emailValid(user.getEmail()) && userService.passwordValid(user.getPassword())){
    //             userService.saveUser(user); 
    //             session.setAttribute("user", user);
    //             return "home";
    //         }
    //     }
    //     return "registeruserform";
    // }

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
        return "registeruserform";
    }

    @GetMapping("/show_recovery_code") // Mapping for the new page
    public String showRecoveryCode(@SessionAttribute("user") User user, Model model) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        Integer recoveryCode = retrievedUser.getRecoveryCode();
        if (recoveryCode != null) {
            model.addAttribute("recoveryCode", recoveryCode);
            return "recoverycode";
        } 
            // Handle case where recovery code is not found in session
       return "error"; // You can create an error page to handle this case
    }

    @PostMapping("/user_signin")
    public String login(@ModelAttribute User user, HttpSession session) {
        if (userService.userLogin(user)) {
            session.setAttribute("user", user);
            return "home";
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
    public String processPasswordReset(Model model, @ModelAttribute User user) {
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        if (retrievedUser == null) {
            model.addAttribute("error", "No user found with the provided email");
            return "forgetPassword";
        }
        
        if (!userService.userRecoveryCode(retrievedUser)) {
            model.addAttribute("error", "Invalid recovery code");
            return "forgetPassword";
        }
    
        if (userService.isNewPasswordDifferent(user, retrievedUser)) {
            model.addAttribute("error", "The new password must be different from the current password");
            return "forgetPassword";
        }

        if (!userService.passwordValid(user.getPassword())) {
            model.addAttribute("error", "The new password must be at least 8 characters long and include at least one uppercase letter and one number");
            return "forgetPassword";
        }
    
        // Update password and clear recovery code
        retrievedUser.setPassword(user.getPassword());
        userService.saveUser(retrievedUser);
    
        return "sign"; // Redirect to the login page or confirmation page
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
        model.addAttribute("user", existingUser);
        return "editProfile";
    }

    @PostMapping("edit_profile")
    public String userEditInformation( @ModelAttribute User user, Model model) { // @RequestParam("image") MultipartFile multipartFile harusnya ada ini su
        // try {
        //     if (!multipartFile.isEmpty()) {
        //         String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        //         user.setimage(fileName);
        //         User updatedUser = userService.saveUser(user);
        //         String uploadDir = "images/" + updatedUser.getUserId();
        //         FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        //     } else {
                userService.saveUser(user);
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        return "redirect:/profile";
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
// }
//     @GetMapping("/upload_profile_picture")
//     public String showProfilePictureUploadForm(Model model, @SessionAttribute("user") User user) {
//     model.addAttribute("user", user);
//     return "profilePicture";
// }

//     @PostMapping("/upload_profile_picture")
//     public String uploadProfilePicture(@RequestParam("file") MultipartFile file, @SessionAttribute("user") User user) {
//         if (!file.isEmpty()) {
//             try {
//                 String fileName = file.getOriginalFilename();
//                 String filePath = Paths.get("uploads", fileName).toString();
//                 Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                
//                 String profilePictureUrl = "/uploads/" + fileName; // Adjust this URL as needed
                
//                 userService.setProfilePicture(user.getEmail(), profilePictureUrl);
                
//                 return "redirect:/profile";
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }
//         return "profile";
//     }

    // @PostMapping("/pause_account")
    // public String pauseAccount(Model model, @SessionAttribute("user") User user) {
    //     // Retrieve user from database to ensure the data is up-to-date
    //     User existingUser = userService.getUserByEmail(user.getEmail());
        
    //     // Check if user status is active
    //     if ("active".equals(existingUser.getStatus())) {
    //         // Set pause start date
    //         existingUser.setPauseStartDate(LocalDate.now());
            
    //         // Set pause end date (30 days from now)
    //         LocalDate pauseEndDate = LocalDate.now().plusDays(30); // 30 days from now
    //         existingUser.setPauseEndDate(pauseEndDate);
            
    //         // Update user status to paused
    //         existingUser.setStatus("paused");

    //         // Save updated user data to database
    //         userService.saveUser(existingUser);
            
    //         // Redirect to profile page
    //         return "redirect:/profile";
    //     } else if ("paused".equals(existingUser.getStatus())) {
    //         // Check if pause end date has passed
    //         LocalDate currentDate = LocalDate.now();
    //         LocalDate pauseEndDate = existingUser.getPauseEndDate();
    //         if (currentDate.isAfter(pauseEndDate)) {
    //             // Update user status to active
    //             existingUser.setStatus("active");
                
    //             // Save updated user data to database
    //             userService.saveUser(existingUser);
                
    //             // Redirect to profile page with notification
    //             return "redirect:/profile?notification=Your account has been reactivated.";
    //         } else {
    //             // User is already paused, redirect to profile with a notification
    //             return "redirect:/profile?notification=Your account is already paused.";
    //         }
    //     } else {
    //         // User is inactive, redirect to profile with a notification
    //         return "redirect:/profile?notification=You must have an active plan to pause your account.";
    //     }
    // }


    // @PostMapping("/unpause_account")
    // public String unpauseAccount(Model model, @SessionAttribute("user") User user) {
    //     // Retrieve user from database to ensure the data is up-to-date
    //     User existingUser = userService.getUserByEmail(user.getEmail());

    //     // Check if the user status is "paused"
    //     if ("paused".equals(existingUser.getStatus())) {
    //         // Hitung durasi pembekuan
    //         LocalDate pauseStartDate = existingUser.getPauseStartDate();
    //         LocalDate today = LocalDate.now();
    //         Duration duration = Duration.between(pauseStartDate.atStartOfDay(), today.atStartOfDay());
    //         long daysPaused = duration.toDays();

    //         // Simpan jumlah hari pembekuan dalam variabel atau di objek User
    //         existingUser.setDaysPaused(daysPaused);

    //         // Set pause start date to null
    //         existingUser.setPauseStartDate(null);
            
    //         // Set pause end date to null
    //         existingUser.setPauseEndDate(null);
            
    //         // Update user status to "active"
    //         existingUser.setStatus("active");
            
    //         // Save updated user data to database
    //         userService.saveUser(existingUser);
    //     }
        
    //     // Redirect to profile page
    //     return "redirect:/profile";
    // }


  }
}
