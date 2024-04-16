package com.fitness.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fitness.fitness.model.Appointment;
// import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.service.AppointmentService;
// import com.fitness.fitness.service.FitnessClassService;
import com.fitness.fitness.service.TrainerService;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TrainerService trainerService;

    // @Autowired
    // private FitnessClassService fitnessClassService;

    @GetMapping("/appointments")
    public String viewAppointments(Model m) {
        // Retrieve and pass appointment records to the view
        List<Appointment> appointments = appointmentService.getAllAppointments();
        m.addAttribute("appointments", appointments);
        return "appointments";
    }

    // GET mapping for editing an appointment
    @GetMapping("/editAppointment/{id}")
    public String editAppointment(@PathVariable("id") Integer id, Model m) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        // Load the list of trainers and add it to the model
        List<Trainer> allTrainers = trainerService.getAllTrainers(); // Assuming you have a TrainerService
        m.addAttribute("allTrainers", allTrainers);
        // List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        // m.addAttribute("allClasses", allClasses);

        m.addAttribute("appointment", appointment);
        return "editAppointment";
    }

    // POST mapping for updating an appointment
    @PostMapping("/updateAppointment")
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.updateAppointment(appointment);
        return "redirect:/viewAppointments"; // Redirect back to the view appointments page
    }   
}
