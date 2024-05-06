package com.fitness.fitness.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.PaymentTransaction;

import jakarta.transaction.Transactional;

@Repository
public interface PaymentTransactionRepo extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByUserUserId(int userId);

    List<PaymentTransaction> findByPurchasedDateBetween(LocalDate startDate, LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM PaymentTransaction p  WHERE p.user.id = :id")
    void deletePaymentTransactionByUserId(@Param("id") int id);
}
