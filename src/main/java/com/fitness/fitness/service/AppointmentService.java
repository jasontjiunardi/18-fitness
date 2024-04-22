package com.fitness.fitness.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.repository.FitnessClassRepo;
import com.fitness.fitness.repository.TrainerRepo;

@Service
public class AppointmentService {

    
    private AppointmentRepo appointmentRepo;

    @Autowired
    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepo.getAppointmentByAppointmentId(appointmentId);
    }

    public List<Integer> getAppointmentIdsByUserId(int userId) {
        return appointmentRepo.findAppointmentIdsByUserId(userId);
    }

    public String getTrainerNameByAppointmentId(int appointmentId) {
        return appointmentRepo.findTrainerNameByAppointmentId(appointmentId);
    }

    public String getClassNameByAppointmentId(int appointmentId){
        return appointmentRepo.findClassNameByAppointmentId(appointmentId);
    }

    public Date getDateByAppointmentId(int appointmentId) {
        return appointmentRepo.findDateByAppointmentId(appointmentId);
    }

}


    

