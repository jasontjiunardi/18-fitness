package com.fitness.fitness.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainer;
    private String classtype;
    private String status;

    //connect to fitness class table
    //@ManyToOne
    //private FitnessClass fitnessClass;

    //public void setFitnessClass(FitnessClass fitnessClass) {
        //this.fitnessClass = fitnessClass;
    //}

     // Default constructor
     public Appointment() {
        this.status = "active"; // Set default value for status
    }

    public Appointment(int id, Date date, String trainer, String classtype, String status) {
        this.id = id;
        this.date = date;
        this.trainer = trainer;
        this.classtype = classtype;
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
    public String getClasstype() {
        return classtype;
    }
    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
