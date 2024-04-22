package com.fitness.fitness.model;

import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "class_Id", nullable = false )  // Assuming the column name in Appointment table
    private FitnessClass fitnessClass;

    @ManyToOne
    @JoinColumn(name="trainer_Id" ,nullable = false )
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name="user_Id" ,nullable = false )
    private User user;


    public Appointment(int appointmentId, Date date) {
        this.appointmentId = appointmentId;
        this.date = date;

    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
