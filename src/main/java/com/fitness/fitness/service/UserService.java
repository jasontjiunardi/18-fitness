package com.fitness.fitness.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    // i use constructor injection instead of field injection to avoid difficulties in testing
    
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
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
        userRepo.updatePasswordByEmail(email, newPassword);
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

    public String setProfilePicture(String email, String profilePictureUrl) {
        User user = userRepo.findByEmail(email);
        user.setProfilePictureUrl(profilePictureUrl);
        userRepo.save(user);
        return profilePictureUrl;
    }
    
    public String getProfilePicture(String email) {
        User user = userRepo.findByEmail(email);
        return user.getProfilePictureUrl();
    }

    public void updateExpiredUsersStatus() {
        LocalDate today = LocalDate.now();
        int updatedCount = userRepo.updateExpiredUsers(today);
        System.out.println(updatedCount + " users have been updated to 'Inactive'.");
    }
    
}
