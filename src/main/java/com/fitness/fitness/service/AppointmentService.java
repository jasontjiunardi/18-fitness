package com.fitness.fitness.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.repository.AppointmentRepo;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepo appointmentRepo;

    //save appointment
    public Appointment saveAppointment(Appointment appointment){
        return appointmentRepo.save(appointment);
    }

    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment getAppointmentById(Integer id) {
        Optional<Appointment> appointmentOptional = appointmentRepo.findById(id);
        return appointmentOptional.orElse(null);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

    //public void deleteAppointment(Integer id) {
       // appointmentRepo.deleteById(id);
    //}
    

    //the function works but the error message wont appear
    public void deleteAppointmentIfActive(Integer appointmentId) {
        // Retrieve the appointment from the repository
        Optional<Appointment> optionalAppointment = appointmentRepo.findById(appointmentId);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
            if (!"active".equals(appointment.getStatus())) {
                throw new IllegalStateException("Only active appointments can be cancelled.");
            }
            // Delete the appointment
            appointmentRepo.delete(appointment);
        }
    }

    
}
