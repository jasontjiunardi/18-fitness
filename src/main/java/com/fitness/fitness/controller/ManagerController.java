package com.fitness.fitness.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fitness.fitness.model.Manager;
import com.fitness.fitness.service.ManagerService;

@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/manager_add_appointment")
    public String showAddAppointmentForm(Model model) {
        Manager manager = new Manager();
        model.addAttribute("appointment", manager);
        return "manager-add-appointment";
    }

    @PostMapping("/manager_add_appointment")
    public String addAppointment(@ModelAttribute("appointment") Manager manager, Model model) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        
        if (manager.getCustomerEmail().isEmpty() || manager.getPreferredTrainer().isEmpty()
                || manager.getClassName().isEmpty() || manager.getDate() == null
                || manager.getTimeSlot() == null) {
            model.addAttribute("error", "All fields are required.");
        } else if (manager.getDate().isBefore(currentDate) ||
                   (manager.getDate().isEqual(currentDate) && manager.getTimeSlot().isBefore(currentTime))) {
            model.addAttribute("error", "You cannot book appointments in the past.");
        } else {
            managerService.manageraddAppointment(manager);
            model.addAttribute("message", "Appointment added successfully!");
        }
        return "manager-add-appointment";
    }
}
