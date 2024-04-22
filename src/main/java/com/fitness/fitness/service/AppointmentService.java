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
    public List<Appointment> filterAppointmentsByClass(String className) {
        // Filter appointments by class name
        return appointmentRepo.findByFitnessclassname(className);
    }

    public List<Appointment> filterAppointmentsByTrainer(String trainerName) {
        return appointmentRepo.findByTrainer(trainerName);
    }
     // Filter appointments by date range
     public List<Appointment> filterAppointmentsByDateRange(Date startDate, Date endDate) {
        return appointmentRepo.findByDateBetween(startDate, endDate);
    }

    // Filter appointments by trainer and fitness class
    public List<Appointment> filterAppointmentsByTrainerAndClass(String trainer, String fitnessClass) {
        return appointmentRepo.findByTrainerAndFitnessclassname(trainer, fitnessClass);
    }

    // Filter appointments by trainer and status
    public List<Appointment> filterAppointmentsByTrainerAndStatus(String trainer, String status) {
        return appointmentRepo.findByTrainerAndStatus(trainer, status);
    }

    // Filter appointments by status and date range
    public List<Appointment> filterAppointmentsByStatusAndDateRange(String status, Date startDate, Date endDate) {
        return appointmentRepo.findByStatusAndDateBetween(status, startDate, endDate);
    }

    // Filter appointments by status, date range, and trainer
    public List<Appointment> filterAppointmentsByStatusAndDateAndTrainer(String status, Date startDate, Date endDate, String trainer) {
        return appointmentRepo.findByStatusAndDateBetweenAndTrainer(status, startDate, endDate, trainer);
    }

    public List<Appointment> filterAppointmentsByStatus(String status) {
        return appointmentRepo.findByStatus(status);
    }
    public List<Appointment> filterAppointmentsByClassAndStatus(String className, String status) {
        return appointmentRepo.findByFitnessclassnameAndStatus(className, status);
    }
    
    public List<Appointment> filterAppointmentsByClassAndDateRange(String className, Date startDate, Date endDate) {
        return appointmentRepo.findByFitnessclassnameAndDateBetween(className, startDate, endDate);
    }
    
    public List<Appointment> filterAppointmentsByClassAndTrainer(String className, String trainer) {
        return appointmentRepo.findByFitnessclassnameAndTrainer(className, trainer);
    }
    
    public List<Appointment> filterAppointmentsByStatusAndTrainer(String status, String trainer) {
        return appointmentRepo.findByStatusAndTrainer(status, trainer);
    }
    
    public List<Appointment> filterAppointmentsByStatusAndClassAndTrainer(String status, String className, String trainer) {
        return appointmentRepo.findByStatusAndFitnessclassnameAndTrainer(status, className, trainer);
    }
    
    public List<Appointment> filterAppointmentsByStatusAndClassAndDateRange(String status, String className, Date startDate, Date endDate) {
        return appointmentRepo.findByStatusAndFitnessclassnameAndDateBetween(status, className, startDate, endDate);
    }
    
    

    public List<Appointment> filterAppointments(String filter, String value, Date startDate, Date endDate) {
        switch (filter) {
            case "trainer":
                return appointmentRepo.findByTrainer(value);
            case "date":
                // Filter appointments by date range
                return appointmentRepo.findByDateBetween(startDate, endDate);
            case "class":
                return appointmentRepo.findByFitnessclassname(value);
            default:
                return getAllAppointments();
        }
    }



}

    

