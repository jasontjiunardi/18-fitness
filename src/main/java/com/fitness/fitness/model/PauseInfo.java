package com.fitness.fitness.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PauseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "pause_start_date")
    private LocalDate pauseStartDate;

    @Column(name = "pause_end_date")
    private LocalDate pauseEndDate;

    @Column(name = "days_paused")
    private long daysPaused;

    private String status;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getPauseStartDate() {
        return pauseStartDate;
    }
    public void setPauseStartDate(LocalDate pauseStartDate) {
        this.pauseStartDate = pauseStartDate;
    }
    public LocalDate getPauseEndDate() {
        return pauseEndDate;
    }
    public void setPauseEndDate(LocalDate pauseEndDate) {
        this.pauseEndDate = pauseEndDate;
    }
    public long getDaysPaused() {
        return daysPaused;
    }
    public void setDaysPaused(long daysPaused) {
        this.daysPaused = daysPaused;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
