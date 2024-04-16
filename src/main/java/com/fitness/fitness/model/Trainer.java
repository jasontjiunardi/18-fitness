package com.fitness.fitness.model;

import jakarta.persistence.Column;
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
    private String imageUrl;
    private String phone;
    @Column(name = "`rank`") // Enclosing rank in backticks to avoid SQL syntax error
    private int rank; //3 for 3 star. 4 for 4 star, 5 for 5 star
    private String trainerSince;
    
    public Trainer() {
    }
    
    public Trainer(int id, String name, int age, String dob, String email, String phone, String trainerSince, int rank,
            String imageUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.trainerSince = trainerSince;
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
    public String getTrainerSince() {
        return trainerSince;
    }
    public void setTrainerSince(String trainerSince) {
        this.trainerSince = trainerSince;
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
