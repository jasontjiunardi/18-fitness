package com.fitness.fitness.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.repository.AppointmentRepo;


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

    public LocalDate getDateByAppointmentId(int appointmentId) {
        return appointmentRepo.findDateByAppointmentId(appointmentId);
    }

    public String getStatusByAppointmentId(int appointmentId){
        return appointmentRepo.findStatusByappointmentId(appointmentId);
    }

    public int deactivatePastAppointments() {
        LocalDate today = LocalDate.now();
        return appointmentRepo.updateStatusForPastAppointments(today);
    }

}


    

