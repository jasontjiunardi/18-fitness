package com.fitness.fitness.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
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
    @Column(name = "appointment_id") 
    private int appointmentId;
    public Appointment() {
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "class_Id", nullable = false )  // Assuming the column name in Appointment table
    private FitnessClass fitnessClass;

    @ManyToOne
    @JoinColumn(name="trainer_Id" ,nullable = false )
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name="user_Id" ,nullable = false )
    private User user;

    private String status;


    public Appointment(int appointmentId, LocalDate date, String status) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.status = status;

    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
