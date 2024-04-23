package com.fitness.fitness.controller;


import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.fitness.fitness.model.User;
import com.fitness.fitness.service.AppointmentService;
import com.fitness.fitness.service.FitnessClassService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;



@Controller
public class AppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final TrainerService trainerService;
    private final FitnessClassService fitnessClassService;

    @Autowired
    public AppointmentController(UserService userService, AppointmentService appointmentService,  TrainerService trainerService, FitnessClassService fitnessClassService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.trainerService = trainerService;
        this.fitnessClassService = fitnessClassService;
    }
    

    @GetMapping("/userAppointment")
    public String userAppointment(Model model, @SessionAttribute("user") User user) {
        // Update the status of past appointments to "inactive"
        appointmentService.deactivatePastAppointments();
    
        User retrievedUser = userService.getUserByEmail(user.getEmail());
        model.addAttribute("retrievedUser", retrievedUser); // Add retrievedUser to the model as an attribute
        int userId = retrievedUser.getUserId(); // ID from user email
    
        List<Integer> appointmentIds = appointmentService.getAppointmentIdsByUserId(userId);
        model.addAttribute("appointmentIds", appointmentIds);
    
        return "userViewAppointments"; 
    }


    public String getTrainerNameByAppointmentId(int appointmentId) {
        return appointmentService.getTrainerNameByAppointmentId(appointmentId);
    }
    public String getClassNameByAppointmentId(int appointmentId) {
        return appointmentService.getClassNameByAppointmentId(appointmentId);
    }
    public LocalDate getDateByAppointmentId(int appointmentId){
        return appointmentService.getDateByAppointmentId(appointmentId);
    }

    public String getStatusByAppointmentId(int appointmentId){
        return appointmentService.getStatusByAppointmentId(appointmentId);
    }

    
    // // Method to fetch trainer ID by appointment ID for Thymeleaf template JGN DI HAPUS KERJA KERAS 6 JAM
    // public int getTrainerIdByAppointmentId(int appointmentId) {
    //     return appointmentService.getTrainerIdByAppointmentId(appointmentId);
    // }

    
}