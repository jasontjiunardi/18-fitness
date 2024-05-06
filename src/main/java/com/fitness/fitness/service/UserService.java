package com.fitness.fitness.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.User;
import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.repository.PaymentTransactionRepo;
import com.fitness.fitness.repository.ReviewRepo;
import com.fitness.fitness.repository.UserRepo;

import jakarta.persistence.CascadeType;


@Service
public class UserService {
    
    private final UserRepo userRepo;
    private final AppointmentRepo appointmentRepo;
    private final PaymentTransactionRepo paymentTransactionRepo;
    private final ReviewRepo reviewRepo;
    @Autowired
    private  AppointmentService appointmentService;
    @Autowired
    private  PaymentTransactionService paymentTransactionService;
    @Autowired
    private  ReviewService reviewService;

    // i use constructor injection instead of field injection to avoid difficulties in testing
    
    public UserService(UserRepo userRepo, AppointmentRepo appointmentRepo,
            PaymentTransactionRepo paymentTransactionRepo, ReviewRepo reviewRepo) {
        this.userRepo = userRepo;
        this.appointmentRepo = appointmentRepo;
        this.paymentTransactionRepo = paymentTransactionRepo;
        this.reviewRepo = reviewRepo;
    }
    
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User saveUser(User user){
        return userRepo.save(user);
    }

    public boolean userLogin(User user) {
        User u = userRepo.findByEmail(user.getEmail());
        if (u != null && u.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }
    
    public void removeUser(String email) {
        User u = userRepo.findByEmail(email);
        int id = u.getUserId();
        
        appointmentRepo.deleteAppointmentByUserId(id);
        paymentTransactionRepo.deletePaymentTransactionByUserId(id);
        reviewRepo.deleteReviewByUserId(id);
        userRepo.delete(u);
       }

    


    public boolean userRecoveryCode(User user) {
        User u = userRepo.findByEmail(user.getEmail());
        if (u != null && u.getRecoveryCode() == (user.getRecoveryCode())) {
            return true;
        }
        return false;
    }
    
    public boolean isNewPasswordDifferent(User user, User retrievedUser) {
        // Check that the new password is indeed different from the existing one
        return retrievedUser.getPassword().equals(user.getPassword());
    }

    public void updatePasswordByEmail(String email, String newPassword) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepo.save(user);
        }
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    public boolean verifyUser(User user) {
        User existingUser = userRepo.findByEmail(user.getEmail());
        return existingUser == null;
    }

    public boolean emailValid(String email) {
        if(email.endsWith("@gmail.com")){
            return true;
        }
        return false;
        
    }

    private boolean containsUppercaseLetterAndNumber(String password) {
        boolean containsUppercase = false;
        boolean containsNumber = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            }
        }
        
        return containsUppercase && containsNumber;
    }
    
    public boolean passwordValid(String password) {
        return password.length() >= 8 &&
               containsUppercaseLetterAndNumber(password);
    }

    public void setCreditCardNumber(String email, String cardNumber) {
        User user = userRepo.findByEmail(email);
        user.setCardNumber(cardNumber);
        userRepo.save(user);
    }
    public void setStatus(String email, String status){
        User user = userRepo.findByEmail(email);
        user.setStatus(status);
        userRepo.save(user);
    }

    public boolean resetPassword(String email, String password, String newPassword) {
        if (newPassword.equals(password)) {
            return false;
        }
    
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            user.setPassword(newPassword);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public boolean validateResetPasswordForm(String password, String newPassword, String confirmPassword) {
        return password.length() >= 8 && newPassword.length() >= 8 &&
               containsUppercaseLetterAndNumber(newPassword) && newPassword.equals(confirmPassword);
    }
    
    public User saveUserProfile(User user) {
        if (user.getPhoto() == null || user.getPhoto().isEmpty()) {
            user.setPhoto("avatar1.jpg");
        }
        return userRepo.save(user);
    }
    public void updateExpiredUsersStatus() {
        LocalDate today = LocalDate.now();
        int updatedCount = userRepo.updateExpiredUsers(today);
        System.out.println(updatedCount + " users have been updated to 'Inactive'.");
    }
    public User getUserById(int userId) {
        // Use the userRepository to find the user by ID
        return userRepo.findById(userId).orElse(null);
    }

}
