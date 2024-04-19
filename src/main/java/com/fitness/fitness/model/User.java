package com.fitness.fitness.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String phoneNumber;
    private String status = "Inactive"; // Plan type or Paused or Inactive
    private Date activeDate = null;
    private int recoveryCode;
    private String cardNumber;

    public User() {
    }

    public User(int id, String name, String username, String password, String email, Date dob, String phoneNumber,
            String status, Date activeDate, int recoveryCode, String cardNumber) {
        this.id = id;
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
    }
    public int getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(int recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public Date getActiveDate(){
        return activeDate;
    }
    public void setActiveDate(Date activeDate){
        this.activeDate = activeDate;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


    
}
