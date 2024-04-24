package com.fitness.fitness.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;


@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;
    private String gender;
    private String dob;
    private String email;
    private String image;
    private String phone;
    @Column(name = "`rank`") // Enclosing rank in backticks to avoid SQL syntax error
    private int rank; //3 for 3 star. 4 for 4 star, 5 for 5 star
    private String trainerSince;
   
    public Trainer() {
    }
    
    @ManyToMany(mappedBy = "trainers")
    private Set<Plan> plans = new HashSet<>();

    // Getter and setter for plans
    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }
    // Relationship with reviews
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Review> reviews;
    
    public Trainer(String name, int age, String gender, String dob, String email, String image, String phone, int rank,
            String trainerSince) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.rank = rank;
        this.trainerSince = trainerSince;
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
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Trainer [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", dob=" + dob
                + ", email=" + email + ", image=" + image + ", phone=" + phone + ", rank=" + rank + ", trainerSince="
                + trainerSince + ", plans=" + plans + "]";
    }


}
