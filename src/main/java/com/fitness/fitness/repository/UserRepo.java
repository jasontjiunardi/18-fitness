package com.fitness.fitness.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitness.fitness.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
    public User findByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = 'Inactive', u.activeDate = NULL WHERE u.activeDate < ?1")
    int updateExpiredUsers(LocalDate currentDate);

}
