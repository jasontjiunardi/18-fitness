package com.fitness.fitness.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

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
    public String viewAppointments(Model model) {
        // Retrieve and pass appointment records to the view
        List<Appointment> appointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", appointments);
        
        // Retrieve and pass available trainer names and class names to the view
        List<Trainer> availableTrainerNames = trainerService.getAllTrainers();
        model.addAttribute("availableTrainerNames", availableTrainerNames);
        
        List<FitnessClass> availableClassNames = fitnessClassService.getAllClasses();
        model.addAttribute("availableClassNames", availableClassNames);
        
        return "appointments";
    }
    
    // GET mapping for editing an appointment
    @GetMapping("/editAppointment/{id}")
    public String editAppointment(@PathVariable("id") Integer id, Model m) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        // Load the list of trainers and add it to the model
        List<Trainer> allTrainers = trainerService.getAllTrainers(); // Assuming you have a TrainerService
        m.addAttribute("allTrainers", allTrainers);
        List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        m.addAttribute("allClasses", allClasses);

        m.addAttribute("appointment", appointment);
        return "editAppointment";
    }

    @GetMapping("/bookAppointment")
public String bookAppointmentForm(@RequestParam(value = "trainerId", required=false) Integer trainerId,
                                  @RequestParam(value = "classId", required=false) Integer classId,
                                  @RequestParam(value = "className", required=false) String className,
                                  Model m) {
        // Load the list of trainers and classes and add them to the model
        m.addAttribute("trainerId", trainerId);
        List<Trainer> allTrainers = trainerService.getAllTrainers();
        m.addAttribute("allTrainers", allTrainers);
        List<FitnessClass> allClasses = fitnessClassService.getAllClasses();
        
        m.addAttribute("allClasses", allClasses);
        // Set minimum date for the date input field to today
        m.addAttribute("minDate", LocalDate.now());
        
        // Add an empty appointment object to bind the form data
        Appointment appointment = new Appointment();
        // Prefill class information if available
        if (classId != null && className != null) {
            appointment.setFitnessclassname(className);
            // You can also set other class-related information here if needed
        }
        m.addAttribute("appointment", appointment);
        if (trainerId != null) {
            m.addAttribute("trainerId", trainerId);
        }
        
        return "bookAppointmentForm"; // Rendering the booking form
    }

    @PostMapping("/bookAppointment")
public String saveAppointment(@ModelAttribute("appointment") Appointment appointment, @RequestParam(value = "trainerName") String trainerName, Model m) {
    // Here you would perform validation on the appointment object

    // Retrieve the trainer object by name from the database
    Trainer trainer = trainerService.findTrainerByName(trainerName);
    if (trainer == null) {
        // Handle the case where the trainer is not found
        // You can show an error message or redirect the user to another page
        return "redirect:/error";
    }

    // Set the trainer object in the appointment
    appointment.setTrainer(trainer.getName());

    // Save the appointment
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
    public String updateAppointment(@ModelAttribute("appointment") @Valid Appointment appointment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("allTrainers", trainerService.getAllTrainers());
            model.addAttribute("allClasses", fitnessClassService.getAllClasses());
            return "editAppointment";
        }
        appointmentService.updateAppointment(appointment);
        return "redirect:/appointments";
    }
    @GetMapping("/applyFilter")
    public String applyFilter(@RequestParam(value = "filter", required = false) String filter,
                              @RequestParam(value = "value", required = false) String value,
                              @RequestParam(value = "classValue", required = false) String classValue,
                              @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                              @RequestParam(value = "selectedFilters", required = false) List<String> selectedFilters,
                              @RequestParam(value = "status", required = false) String status,
                              Model model) {
        List<Appointment> filteredAppointments = new ArrayList<>();
    
        if (selectedFilters != null && !selectedFilters.isEmpty()) {
            // Check each scenario based on selected filters
            if (selectedFilters.contains("status") && status != null && !status.isEmpty()) {
                // Filter by status
                filteredAppointments = appointmentService.filterAppointmentsByStatus(status);
            }
            if (selectedFilters.contains("dateAndTime")) {
                // Filter by date and time
                filteredAppointments = appointmentService.filterAppointmentsByDateRange(startDate, endDate);
            }
            if (selectedFilters.contains("trainer")) {
                // Filter by trainer name
                filteredAppointments = appointmentService.filterAppointmentsByTrainer(value);
            }
            if (selectedFilters.contains("status")) {
                // Filter by status
                filteredAppointments = appointmentService.filterAppointmentsByStatus(value);
            }
            if (selectedFilters.contains("class")) {
                // Filter by class name
                filteredAppointments = appointmentService.filterAppointmentsByClass(classValue);
            }
            if (selectedFilters.contains("trainer") && selectedFilters.contains("class")) {
                // Filter by both trainer name and class name
                filteredAppointments = appointmentService.filterAppointmentsByTrainerAndClass(value, classValue);
            }
            if (selectedFilters.contains("trainer") && selectedFilters.contains("status")) {
                // Filter by trainer and status
                filteredAppointments = appointmentService.filterAppointmentsByTrainerAndStatus(value, classValue);
            }
            if (selectedFilters.contains("status") && selectedFilters.contains("dateAndTime")) {
                // Filter by status and date and time
                filteredAppointments = appointmentService.filterAppointmentsByStatusAndDateRange(value, startDate, endDate);
            }
            if (selectedFilters.contains("status") && selectedFilters.contains("dateAndTime") && selectedFilters.contains("trainer")) {
                // Filter by status, date and time, and trainer
                filteredAppointments = appointmentService.filterAppointmentsByStatusAndDateAndTrainer(value, startDate, endDate, classValue);
            }
            // Additional filter combinations
            if (selectedFilters.contains("class") && selectedFilters.contains("status")) {
                // Filter by class name and status
                filteredAppointments = appointmentService.filterAppointmentsByClassAndStatus(classValue, value);
            }
            if (selectedFilters.contains("class") && selectedFilters.contains("dateAndTime")) {
                // Filter by class name and date and time
                filteredAppointments = appointmentService.filterAppointmentsByClassAndDateRange(classValue, startDate, endDate);
            }
            if (selectedFilters.contains("class") && selectedFilters.contains("trainer")) {
                // Filter by class name and trainer
                filteredAppointments = appointmentService.filterAppointmentsByClassAndTrainer(classValue, value);
            }
            if (selectedFilters.contains("status") && selectedFilters.contains("trainer")) {
                // Filter by status and trainer
                filteredAppointments = appointmentService.filterAppointmentsByStatusAndTrainer(value, classValue);
            }
            if (selectedFilters.contains("status") && selectedFilters.contains("class") && selectedFilters.contains("trainer")) {
                // Filter by status, class, and trainer
                filteredAppointments = appointmentService.filterAppointmentsByStatusAndClassAndTrainer(value, classValue, value);
            }
            if (selectedFilters.contains("status") && selectedFilters.contains("class") && selectedFilters.contains("dateAndTime")) {
                // Filter by status, class, and date and time
                filteredAppointments = appointmentService.filterAppointmentsByStatusAndClassAndDateRange(value, classValue, startDate, endDate);
            }
        } else {
            // Default to showing all appointments if no filters selected
            filteredAppointments = appointmentService.getAllAppointments();
        }
    
        // Add filtered appointments to the model
        model.addAttribute("appointments", filteredAppointments);
    
        // Add available trainer names, class names, and other necessary data to the model for filtering options
        // Add available trainer names and class names to the model for filtering options
        List<Trainer> availableTrainerNames = trainerService.getAllTrainers();
        model.addAttribute("availableTrainerNames", availableTrainerNames);
    
        List<FitnessClass> availableClassNames = fitnessClassService.getAllClasses();
        model.addAttribute("availableClassNames", availableClassNames);
    
        // Add any other data you need for filtering options
    
        return "appointments";
    }

// private List<Appointment> filterAppointmentsByDateRange(List<Appointment> appointments, Date startDate, Date endDate) {
//     // Filter appointments by date range
//     return appointments.stream()
//             .filter(appointment -> !appointment.getDate().before(startDate) && !appointment.getDate().after(endDate))
//             .collect(Collectors.toList());
// }


}
