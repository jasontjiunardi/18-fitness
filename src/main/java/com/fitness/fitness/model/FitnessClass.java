package com.fitness.fitness.model;



import java.util.ArrayList;
import java.util.List;
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
    private int classId;
    private String className;
    private String classDescription;
    private String classDuration;
    
    @OneToMany(mappedBy = "fitnessClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    public FitnessClass(String className, String classDescription, String classDuration) {
        this.className = className;
        this.classDescription = classDescription;
        this.classDuration = classDuration;

    }

    public FitnessClass(){
        
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public String getClassDuration() {
        return classDuration;
    }

    public void setClassDuration(String classDuration) {
        this.classDuration = classDuration;
    }

    public void getFitnessClassId(int classId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFitnessClassId'");
    }
}

