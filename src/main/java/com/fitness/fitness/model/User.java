package com.fitness.fitness.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String username;
    private String password;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String phoneNumber;
    private String status = "Inactive"; // Plan type or Paused or Inactive
    private LocalDate activeDate = null;
    private int recoveryCode;
    private String cardNumber;
    private String profilePictureUrl;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    public User() {
    }

    
    // Define one-to-one relationship with Plan
    @OneToOne(mappedBy = "user")
    private Plan plan;

    public User(int userId, String name, String username, String password, String email, Date dob, String phoneNumber,
            String status, LocalDate activeDate, int recoveryCode, String cardNumber) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.activeDate = activeDate;
        this.recoveryCode = recoveryCode;
        this.cardNumber = cardNumber;
        this.profilePictureUrl = profilePictureUrl;
    }
    
    public int getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(int recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDate getActiveDate(){
        return activeDate;
    }
    public void setActiveDate(LocalDate activeDate){
        this.activeDate = activeDate;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    
}
