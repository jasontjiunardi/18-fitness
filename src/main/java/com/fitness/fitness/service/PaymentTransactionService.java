package com.fitness.fitness.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.fitness.fitness.model.PaymentTransaction;
import com.fitness.fitness.repository.PaymentTransactionRepo;

@Service
public class PaymentTransactionService {
    private final PaymentTransactionRepo paymentTransactionRepo;

    public PaymentTransactionService(PaymentTransactionRepo paymentTransactionRepo) {
        this.paymentTransactionRepo = paymentTransactionRepo;
    }

    public void deleteAllPaymentTransactionsbyUserId(int userId) {
        // TODO Auto-generated method stub
        List<PaymentTransaction> paymentTransactions = paymentTransactionRepo.findByUserUserId(userId);
        for (PaymentTransaction paymentTransaction : paymentTransactions) {
            paymentTransactionRepo.delete(paymentTransaction);
        }
    }
}
