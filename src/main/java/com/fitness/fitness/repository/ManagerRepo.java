package com.fitness.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.fitness.model.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Long> {
}
