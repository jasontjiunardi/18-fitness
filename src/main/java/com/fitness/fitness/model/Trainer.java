package com.fitness.fitness.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String dob;
    private String email;
    private String phone;
    private String trainer_since;
    private int rank; 
    private String imageUrl;// 3 for 3 star, 4 for 4-star, 5 for 5-star

    
    
    
    public Trainer(int id, String name, int age, String dob, String email, String phone, String trainer_since, int rank,
            String imageUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.trainer_since = trainer_since;
        this.rank = rank;
        this.imageUrl = imageUrl;
    }
    public int getId() {
        return id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTrainer_since() {
        return trainer_since;
    }
    public void setTrainer_since(String trainer_since) {
        this.trainer_since = trainer_since;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
