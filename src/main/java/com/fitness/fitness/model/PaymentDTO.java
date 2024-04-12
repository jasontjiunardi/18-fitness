package com.fitness.fitness.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PaymentDTO {
    private String cardholderName;
    private String cardNumber;
    private String cvv;
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date expiryDate;
    private String phoneNumber;
    private int userId; 
    private String paymentMethod;

    public PaymentDTO() {}

    public PaymentDTO(String cardholderName, String cardNumber, String cvv, String expiryDate, String phoneNumber, int userId, String paymentMethod) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        try {
            this.expiryDate = new SimpleDateFormat("yyyy-MM").parse(expiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
