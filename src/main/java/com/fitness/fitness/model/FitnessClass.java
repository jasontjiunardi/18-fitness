package com.fitness.fitness.model;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class FitnessClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fitnessclassname;
    private String classdescription;
    private String classduration;
    @OneToMany(mappedBy = "fitnessClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PaymentTransaction> PaymentTransaction = new HashSet<>();

    public FitnessClass(){
        
    }
    
    
    public FitnessClass(int id, String fitnessclassname, String classdescription, String classduration) {
        this.id = id;
        this.fitnessclassname = fitnessclassname;
        this.classdescription = classdescription;
        this.classduration = classduration;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getFitnessclassname() {
        return fitnessclassname;
    }


    public void setFitnessclassname(String fitnessclassname) {
        this.fitnessclassname = fitnessclassname;
    }


    public String getClassdescription() {
        return classdescription;
    }


    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }


    public String getClassduration() {
        return classduration;
    }


    public void setClassduration(String classduration) {
        this.classduration = classduration;
    }


    
}

