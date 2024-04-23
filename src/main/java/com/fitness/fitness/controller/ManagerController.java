package com.fitness.fitness.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.FileUploadUtil;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.repository.UserRepo;
import com.fitness.fitness.service.ManagerService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;



@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainerRepo trainerRepo; 

    @Autowired
    private TrainerService trainerService;
    @Autowired
    private UserRepo userRepo;
    
    //  cb cek ulang ini
    // @GetMapping("/manager_add_appointment")
    // public String showAddAppointmentForm(Model model) {
    //     List<Trainer> trainers = trainerRepo.findAll();
    //     Manager manager = new Manager();
    //     LocalDate currentDate = LocalDate.now();
    //     model.addAttribute("trainers", trainers);
    //     model.addAttribute("appointment", manager);
    //     model.addAttribute("currentDate", currentDate);
    //     return "manager-add-appointment";
    // }

    // @PostMapping("/manager_add_appointment")
    // public String addAppointment(@ModelAttribute("appointment") Manager manager, Model model) {
    //     LocalDate currentDate = LocalDate.now();
    //     LocalTime currentTime = LocalTime.now();
        
    //     // Check if the entered email corresponds to an existing user
    //     User user = userService.getUserByEmail(manager.getCustomerEmail());
    //     if (user == null) {
    //         model.addAttribute("error", "No user found with the entered email.");
    //         // Retrieve the list of trainers and add it to the model
    //         List<Trainer> trainers = trainerRepo.findAll();
    //         model.addAttribute("trainers", trainers);
    //         model.addAttribute("currentDate", currentDate); // Add currentDate to the model
    //         return "manager-add-appointment";
    //     }

    //     if (manager.getPreferredTrainer().isEmpty()
    //             || manager.getClassName().isEmpty() || manager.getDate() == null
    //             || manager.getTimeSlot() == null) {
    //         model.addAttribute("error", "All fields are required.");
    //     } else if (manager.getDate().isBefore(currentDate) ||
    //                (manager.getDate().isEqual(currentDate) && manager.getTimeSlot().isBefore(currentTime))) {
    //         model.addAttribute("error", "You cannot book appointments in the past.");
    //     } else {
    //         managerService.manageraddAppointment(manager);
    //         model.addAttribute("message", "Appointment added successfully!");
    //     }
        
    //     // Retrieve the list of trainers and add it to the model
    //     List<Trainer> trainers = trainerRepo.findAll();
    //     model.addAttribute("trainers", trainers);
    //     model.addAttribute("currentDate", currentDate); // Add currentDate to the model
        
    //     return "manager-add-appointment";
    // }

    //arden buat untuk data manager pas signin jdi perlu ada email sm password di manager model
    @GetMapping("/manager_signin")
    public String showLoginForm(Model model) {
        User existingUser = new User();
        model.addAttribute("user", existingUser ) ;
        return "sign_manager";
    }
    
    @PostMapping("/manager_signin")
    public String showMangagerHomePage(){
        return "manager_home";
    }

    @GetMapping("/managerViewTrainers")
    public String showTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "managerViewTrainers";
    }
    @GetMapping("/managerViewUsers")
    public String showUsers(Model model) {
        model.addAttribute("Users", userService.getAllUsers());
        return "managerViewUsers";
    }
  
  
    

    @GetMapping("/managerAddTrainer")
    public String showAddTrainerForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "managerAddTrainer";
    }

    @PostMapping("/managerSaveTrainer")
    public String saveTrainer(@ModelAttribute("trainer") Trainer trainer, Model model) {
        trainerService.saveTrainer(trainer);
        return "redirect:/managerViewTrainers";
    }
    
    @GetMapping("/editTrainer/{id}")
    public String editTrainer(@PathVariable("id") int id, Model model) {
        // Fetch the trainer from the database by id
        Trainer trainer = trainerService.getTrainerById(id);
        model.addAttribute("trainer", trainer);
        return "editTrainer";
    }

    @PostMapping("/updateTrainer")
    public String updateTrainer(@RequestParam("image") MultipartFile multipartFile,@ModelAttribute Trainer trainer, Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            trainer.setImage(fileName);
            Trainer savedTrainer = trainerService.saveTrainer(trainer);
            String upload = "images/" + trainer.getId(); // Adjust this URL as needed
            
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
      
    }   else{
        if (trainer.getImage().isEmpty()) {
            trainer.setImage("wechat_icon.jpg");
            trainerService.saveTrainer(trainer);
        }
    } 
        trainerService.saveTrainer(trainer);// Update the trainer information in the database
        trainerService.updateTrainer(trainer);
        return "managerViewTrainers";
    }

    @PostMapping("/removeTrainer/{id}")
    public String removeTrainer(@PathVariable("id") int id) {
        // Remove the trainer from the database
        trainerService.removeTrainer(id);
        return "redirect:/managerViewTrainers";
    }
}
