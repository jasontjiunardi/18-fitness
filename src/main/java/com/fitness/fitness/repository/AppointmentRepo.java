package com.fitness.fitness.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Appointment;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDateBetween(Date startDate, Date endDate);
    List<Appointment> findByTrainer(String trainer);
    List<Appointment> findByFitnessclassname(String fitnessclassname);
    List<Appointment> findByTrainerAndFitnessclassname(String trainer, String fitnessclassname);
    List<Appointment> findByTrainerAndStatus(String trainer, String status);
    List<Appointment> findByStatusAndDateBetween(String status, Date startDate, Date endDate);
    List<Appointment> findByStatusAndDateBetweenAndTrainer(String status, Date startDate, Date endDate, String trainer);
    List<Appointment> findByFitnessclassnameAndStatus(String className, String status);
    List<Appointment> findByFitnessclassnameAndDateBetween(String className, Date startDate, Date endDate);
    List<Appointment> findByFitnessclassnameAndTrainer(String className, String trainer);
    List<Appointment> findByStatusAndTrainer(String status, String trainer);
    List<Appointment> findByStatusAndFitnessclassnameAndTrainer(String status, String className, String trainer);
    List<Appointment> findByStatusAndFitnessclassnameAndDateBetween(String status, String className, Date startDate, Date endDate);
    List<Appointment> findByStatus(String status);
}
