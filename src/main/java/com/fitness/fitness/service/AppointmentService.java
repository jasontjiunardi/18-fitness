package com.fitness.fitness.service;

import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.repository.AppointmentRepo;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    // public Appointment getAppointmentById(Integer id) {
    //     Optional<Appointment> appointmentOptional = appointmentRepo.findById(id);
    //     return appointmentOptional.orElse(null);
    // }

    // public void updateAppointment(Appointment appointment) {
    //     appointmentRepo.save(appointment);
    // }

    
}
