// package com.fitness.fitness.configuration;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.fitness.fitness.service.UserService;

// import jakarta.annotation.PostConstruct;
// //this class will check any user valid date and update accordingly if the valid date is expired - Jo

// @Component
// public class UserStatusChecker {
//     @Autowired
//     private UserService userService;

//     @PostConstruct
//     public void initializeUserStatuses() {
//         userService.updateExpiredUsersStatus();
//         System.out.println("User statuses have been checked and updated as necessary.");
//     }
// }
