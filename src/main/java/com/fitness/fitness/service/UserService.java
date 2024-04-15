package com.fitness.fitness.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

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

    public boolean newPassword(User user) {
        User u = userRepo.findByEmail(user.getEmail());
        if (u != null && !u.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
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

}






