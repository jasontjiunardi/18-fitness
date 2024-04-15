package com.fitness.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Appointment;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    public Appointment findById(int id);
    
}
