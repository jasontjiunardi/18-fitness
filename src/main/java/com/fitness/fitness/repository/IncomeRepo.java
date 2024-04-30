package com.fitness.fitness.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.Income;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {

    Income findByIncomeId(String transactionId);

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);
}