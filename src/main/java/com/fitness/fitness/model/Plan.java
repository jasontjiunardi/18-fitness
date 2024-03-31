package com.fitness.fitness.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String planType; //silver or gold or diamond
    private int durationType; //6 or 12 months
    private double price;
    public Plan() {
    }
    public Plan(String planType, int durationType, double price) {
        this.planType = planType;
        this.durationType = durationType;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlanType() {
        return planType;
    }
    public void setPlanType(String planType) {
        this.planType = planType;
    }
    public int getDurationType() {
        return durationType;
    }
    public void setDurationType(int durationType) {
        this.durationType = durationType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
}
