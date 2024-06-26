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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String photo;
    private String gender;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PauseInfo pauseInfo;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    public User() {
    }

    public User(int userId, String name, String username, String password, String email, Date dob, String phoneNumber,
            String status, LocalDate activeDate, int recoveryCode, String cardNumber, String photo, String gender,
            List<Appointment> appointments) {
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
        this.photo = photo;
        this.gender = gender;
        this.appointments = appointments;
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

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(LocalDate activeDate) {
        this.activeDate = activeDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPhoto() {
        if (photo == null) {
            photo ="https://tse2.mm.bing.net/th?id=OIP.PoS7waY4-VeqgNuBSxVUogAAAA&pid=Api&P=0&h=220";
        }
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", name=" + name + ", username=" + username + ", password=" + password
                + ", email=" + email + ", dob=" + dob + ", phoneNumber=" + phoneNumber + ", status=" + status
                + ", activeDate=" + activeDate + ", recoveryCode=" + recoveryCode + ", cardNumber=" + cardNumber
                + ", photo=" + photo + ", gender=" + gender + ", appointments=" + appointments + "]";
    }

    public PauseInfo getPauseInfo() {
        return pauseInfo;
    }

    public void setPauseInfo(PauseInfo pauseInfo) {
        this.pauseInfo = pauseInfo;
    }

}
