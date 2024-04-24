package com.fitness.fitness.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.service.AppointmentService;
import com.fitness.fitness.service.FitnessClassService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;




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
    public LocalDateTime getDateTimeByAppointmentId(int appointmentId){
        return appointmentService.getDateTimeByAppointmentId(appointmentId);
    }

    public String getStatusByAppointmentId(int appointmentId){
        return appointmentService.getStatusByAppointmentId(appointmentId);
    }

    public int getUserIdByAppointmentId(int appointmentId){
        return appointmentService.getUserIdByAppointmentId(appointmentId);
    }

    @GetMapping("/edit_appointment_page")
    public String editAppointmentPage(@RequestParam("appointmentId") int appointmentId, Model model, @SessionAttribute("user") User user) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        List<FitnessClass> classList = fitnessClassService.getAllClasses();
        List<Trainer> trainerList = trainerService.getAllTrainers();

        int userId = appointmentService.getUserIdByAppointmentId(appointmentId);

        // Formatting date and time for the frontend
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTimeNow = now.format(formatter);
        String formattedAppointmentDateTime = appointment.getDate().format(formatter);

        model.addAttribute("userId", userId);
        model.addAttribute("appointment", appointment);
        model.addAttribute("classList", classList);
        model.addAttribute("trainerList", trainerList);
        model.addAttribute("status", appointment.getStatus());
        model.addAttribute("formattedDateTimeNow", formattedDateTimeNow);
        model.addAttribute("formattedAppointmentDateTime", formattedAppointmentDateTime);

        return "editAppointment";
    }

        @PostMapping("/save_appointment")
        public String saveAppointment(@ModelAttribute Appointment appointment, 
                                      @SessionAttribute("user") User user,
                                      @RequestParam("classId") int classId,
                                      @RequestParam("trainerId") int trainerId,
                                      @RequestParam("datetime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime datetime) { {

        // Fetch the related FitnessClass and Trainer objects based on the IDs provided
        FitnessClass fitnessClass = fitnessClassService.getClassById(classId);
        Trainer trainer = trainerService.getTrainerById(trainerId);

        User retrivedUser = userService.getUserByEmail(user.getEmail());

        // Set the fetched entities to the appointment
        appointment.setFitnessClass(fitnessClass);
        appointment.setTrainer(trainer);

        // Set the user from session to the appointment
        appointment.setUser(retrivedUser); // This uses the User object from session to maintain integrity
        appointment.setDate(datetime);

        // Save the appointment to the database
        appointmentService.updateAppointment(appointment);

        return "redirect:/userAppointment"; // Redirect to a confirmation or listing page
        }
}
}

    
    // // Method to fetch trainer ID by appointment ID for Thymeleaf template JGN DI HAPUS KERJA KERAS 6 JAM
    // public int getTrainerIdByAppointmentId(int appointmentId) {
    //     return appointmentService.getTrainerIdByAppointmentId(appointmentId);
    // }

    
