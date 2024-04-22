package com.fitness.fitness.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainer;
    private String fitnessclassname;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    //connect to fitness class table
    @ManyToOne
    @JoinColumn(name = "fitnessClass_id", nullable = false)
    private FitnessClass fitnessClass;

    // Default constructor
     public Appointment() {
        this.status = "active"; // Set default value for status
    }

    public Appointment(int id, Date date, String trainer, String fitnessclassname, String status) {
        this.id = id;
        this.date = date;
        this.trainer = trainer;
        this.fitnessclassname = fitnessclassname;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTrainer() {
        return trainer;
    }
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
    public String getFitnessclassname() {
        return fitnessclassname;
    }
    public void setFitnessclassname(String fitnessclassname) {
        this.fitnessclassname = fitnessclassname;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public FitnessClass getFitnessClass() {
        return fitnessClass;
    }

    public void setFitnessClass(FitnessClass fitnessClass) {
        this.fitnessClass = fitnessClass;
    }
    
}
