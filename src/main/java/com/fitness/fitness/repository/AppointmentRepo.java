package com.fitness.fitness.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Appointment;

import jakarta.transaction.Transactional;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    Appointment getAppointmentByAppointmentId(int appointmentId);
    
    @Query("SELECT a.appointmentId FROM Appointment a WHERE a.user.id = :userId")
    List<Integer> findAppointmentIdsByUserId(@Param("userId") int userId);
    
    @Query("SELECT t.name FROM Trainer t WHERE t.id = (SELECT a.trainer.id FROM Appointment a WHERE a.appointmentId = :appointmentId)")
    String findTrainerNameByAppointmentId(@Param("appointmentId") int appointmentId);
    
    @Query("SELECT f.className FROM Appointment a JOIN a.fitnessClass f WHERE a.appointmentId = :appointmentId")
    String findClassNameByAppointmentId(@Param("appointmentId") int appointmentId);
    
    @Query("SELECT a.date FROM Appointment a WHERE a.appointmentId = :appointmentId")
    LocalDateTime findDateTimeByAppointmentId(@Param("appointmentId") int appointmentId);

    @Query("SELECT a.status FROM Appointment a WHERE a.appointmentId = :appointmentId")
    String findStatusByappointmentId(@Param("appointmentId") int appointmentId);

    @Transactional
    @Modifying
    @Query("UPDATE Appointment a SET a.status = 'inactive' WHERE a.date < :currentDateTime")
    int updateStatusForPastAppointments(@Param("currentDateTime") LocalDateTime currentDateTime); 

    
}


