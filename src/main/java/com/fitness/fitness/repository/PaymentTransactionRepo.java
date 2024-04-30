package com.fitness.fitness.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.PaymentTransaction;

@Repository
public interface PaymentTransactionRepo extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByUserUserId(int userId);

    List<PaymentTransaction> findByPurchasedDateBetween(LocalDate startDate, LocalDate endDate);
}
