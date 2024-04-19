package com.fitness.fitness.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.FitnessClass;
// import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.service.AppointmentService;
import com.fitness.fitness.service.FitnessClassService;
// import com.fitness.fitness.service.FitnessClassService;
import com.fitness.fitness.service.TrainerService;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private FitnessClassService fitnessClassService;

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

    @GetMapping("/bookAppointment")
    public String bookAppointmentForm(@RequestParam(value = "trainerId", required=false) Integer trainerId,Model m) {
        // Load the list of trainers and classes and add them to the model
        List<Trainer> allTrainers = trainerService.getAllTrainers();
            m.addAttribute("allTrainers", allTrainers);
        List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        
        m.addAttribute("allClasses", allClasses);
        // Set minimum date for the date input field to today
        m.addAttribute("minDate", LocalDate.now());
        
        // Add an empty appointment object to bind the form data
        m.addAttribute("appointment", new Appointment());
        if (trainerId != null) {
            m.addAttribute("trainerId", trainerId);
        }
        
        
        return "bookAppointmentForm"; // Rendering the booking form
    }

    @PostMapping("/bookAppointment")
public String saveAppointment(@ModelAttribute("appointment") Appointment appointment, Model m) {
    // Here you would perform validation on the appointment object
    
    // Assuming appointmentService is a service responsible for saving appointments
    appointmentService.saveAppointment(appointment);
    
    // Retrieve the list of trainers and classes again
    List<Trainer> allTrainers = trainerService.getAllTrainers();
    List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
    
    // Set minimum date for the date input field to today
    LocalDate minDate = LocalDate.now();
    
    // Add the necessary attributes to the model
    m.addAttribute("allTrainers", allTrainers);
    m.addAttribute("allClasses", allClasses);
    m.addAttribute("minDate", minDate);
    
    return "bookAppointmentForm"; // Redirecting back to the booking form with updated data
}


/*@PostMapping("/deleteAppointment")
public String deleteAppointment(@RequestParam("appointmentId") Integer appointmentId) {
    appointmentService.deleteAppointment(appointmentId);
    return "redirect:/appointments"; // Redirect back to the appointments page
}*/

@PostMapping("/deleteAppointment")
public String deleteAppointment(@RequestParam("appointmentId") Integer appointmentId, Model model) {
    try {
        // Delegate the deletion logic to the service layer
        appointmentService.deleteAppointmentIfActive(appointmentId);
    } catch (IllegalStateException ex) {
        model.addAttribute("error", ex.getMessage());
        // Add appointment list to model to refresh the appointments table
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        // Return to the appointments page
        return "appointments";
    }

    // Redirect back to the appointments page
    return "redirect:/appointments";
}


    

    // POST mapping for updating an appointment
    @PostMapping("/updateAppointment")
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.updateAppointment(appointment);
        return "redirect:/viewAppointments"; // Redirect back to the view appointments page
    }   
}
