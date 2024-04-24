package com.fitness.fitness.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.repository.AppointmentRepo;

import jakarta.transaction.Transactional;


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

    public LocalDateTime getDateTimeByAppointmentId(int appointmentId) {
        return appointmentRepo.findDateTimeByAppointmentId(appointmentId);
    }

    public String getStatusByAppointmentId(int appointmentId){
        return appointmentRepo.findStatusByappointmentId(appointmentId);
    }

    public int getUserIdByAppointmentId(int appointmentId) {
    Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow();
    return appointment.getUser().getUserId();
    }

    public int deactivatePastAppointments() {
    LocalDateTime currentDateTime = LocalDateTime.now();
    return appointmentRepo.updateStatusForPastAppointments(currentDateTime);
    }

    @Transactional
    public void updateAppointment(Appointment appointment) {
            appointmentRepo.save(appointment); // save method handles both insert and update

}
//its the same as the update appointment above but this one i just create a separate method to make it more separate and clear - herman
    @Transactional
    public void saveAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
    }

}


    

