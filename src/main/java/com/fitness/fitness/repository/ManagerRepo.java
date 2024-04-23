package com.fitness.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Manager;


@Repository
public interface ManagerRepo extends JpaRepository<Manager, Integer> {
    public Manager findByEmail(String email);

}
