package com.fitness.fitness.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitness.FileUploadUtil;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.Manager;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.repository.UserRepo;
import com.fitness.fitness.service.AppointmentService;
import com.fitness.fitness.service.ManagerService;
import com.fitness.fitness.service.PlanService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


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
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PlanService planService;

    // cb cek ulang ini
    // @GetMapping("/manager_add_appointment")
    // public String showAddAppointmentForm(Model model) {
    // List<Trainer> trainers = trainerRepo.findAll();
    // Manager manager = new Manager();
    // LocalDate currentDate = LocalDate.now();
    // model.addAttribute("trainers", trainers);
    // model.addAttribute("appointment", manager);
    // model.addAttribute("currentDate", currentDate);
    // return "manager-add-appointment";
    // }

    // @PostMapping("/manager_add_appointment")
    // public String addAppointment(@ModelAttribute("appointment") Manager manager,
    // Model model) {
    // LocalDate currentDate = LocalDate.now();
    // LocalTime currentTime = LocalTime.now();

    // // Check if the entered email corresponds to an existing user
    // User user = userService.getUserByEmail(manager.getCustomerEmail());
    // if (user == null) {
    // model.addAttribute("error", "No user found with the entered email.");
    // // Retrieve the list of trainers and add it to the model
    // List<Trainer> trainers = trainerRepo.findAll();
    // model.addAttribute("trainers", trainers);
    // model.addAttribute("currentDate", currentDate); // Add currentDate to the
    // model
    // return "manager-add-appointment";
    // }

<<<<<<< HEAD
    // if (manager.getPreferredTrainer().isEmpty()
    // || manager.getClassName().isEmpty() || manager.getDate() == null
    // || manager.getTimeSlot() == null) {
    // model.addAttribute("error", "All fields are required.");
    // } else if (manager.getDate().isBefore(currentDate) ||
    // (manager.getDate().isEqual(currentDate) &&
    // manager.getTimeSlot().isBefore(currentTime))) {
    // model.addAttribute("error", "You cannot book appointments in the past.");
    // } else {
    // managerService.manageraddAppointment(manager);
    // model.addAttribute("message", "Appointment added successfully!");
    // }

    // // Retrieve the list of trainers and add it to the model
    // List<Trainer> trainers = trainerRepo.findAll();
    // model.addAttribute("trainers", trainers);
    // model.addAttribute("currentDate", currentDate); // Add currentDate to the
    // model

    // return "manager-add-appointment";
    // }

=======
>>>>>>> e54b15a769d7999de4f2cf6066f7c51b57756e4e
    @GetMapping("/manager_home_page")
    public String getHomePage(Model model, @SessionAttribute("manager") Manager manager) {
        if (manager != null) {
            model.addAttribute("manager", manager);
            return "manager_home";
        } else {
            return "redirect:/manager_signin";
        }
    }
<<<<<<< HEAD

=======
 
>>>>>>> e54b15a769d7999de4f2cf6066f7c51b57756e4e
    @GetMapping("/manager_signin")
    public String showLoginForm(Model model, HttpSession session) {
        session.invalidate();
        Manager existingManager = new Manager();
        model.addAttribute("manager", existingManager);
        return "sign_manager";
    }

    @PostMapping("/manager_signin")
    public String login(@ModelAttribute Manager manager, HttpSession session) {
        if (managerService.managerLogin(manager)) {
            session.setAttribute("manager", manager);
            return "manager_home";
        }
        return "login_fail_manager";
    }

    @GetMapping("/managerViewTrainers")
    public String showTrainers(Model model, @SessionAttribute("manager") Manager manager) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "managerViewTrainers";
    }
<<<<<<< HEAD

=======
    @GetMapping("/managerViewUsers")
    public String showUsers(Model model) {
        model.addAttribute("Users", userService.getAllUsers());
        return "managerViewUsers";
    }
  
>>>>>>> e54b15a769d7999de4f2cf6066f7c51b57756e4e
    @GetMapping("/managerAddTrainer")
    public String showAddTrainerForm(Model model, @SessionAttribute("manager") Manager manager) {
        model.addAttribute("trainer", new Trainer());
        return "managerAddTrainer";
    }

    @PostMapping("/managerSaveTrainer")
    public String saveTrainer(@ModelAttribute("trainer") Trainer trainer, Model model,
            @SessionAttribute("manager") Manager manager) {
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
    public String updateTrainer(@RequestParam("image") MultipartFile multipartFile, @ModelAttribute Trainer trainer,
            Model model) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            trainer.setPhoto(fileName);
            String upload = "src/main/resources/static/images/"; // Adjust this URL as needed
            FileUploadUtil.saveFile(upload, fileName, multipartFile);
<<<<<<< HEAD

        } else {
            if (trainer.getPhoto().isEmpty()) {
                trainer.setPhoto("wechat_icon.jpg");
                trainerService.updateTrainer(trainer);
            }
=======
      
        } else {
        if (trainer.getImage().isEmpty()) {
            trainer.setImage("wechat_icon.jpg");
            trainerService.saveTrainer(trainer);
>>>>>>> e54b15a769d7999de4f2cf6066f7c51b57756e4e
        }
        trainerService.saveTrainer(trainer);
        trainerService.updateTrainer(trainer);
        return "managerViewTrainers";
    }

    @PostMapping("/removeTrainer/{id}")
    public String removeTrainer(@PathVariable("id") int id) {
        // Remove the trainer from the database
        trainerService.removeTrainer(id);
        return "redirect:/managerViewTrainers";
    }

    @PostMapping("/promoteTrainer/{id}")
public String promoteTrainer(@PathVariable("id") int id) {
    Trainer trainer = trainerService.getTrainerById(id);
    if (trainer != null) {
        int currentRank = trainer.getRank();
        if (currentRank < 5) {
            trainer.updateRank(currentRank + 1);
            trainerService.saveTrainer(trainer);
        }
    }
    return "redirect:/managerViewTrainers";
}

@PostMapping("/demoteTrainer/{id}")
public String demoteTrainer(@PathVariable("id") int id) {
    Trainer trainer = trainerService.getTrainerById(id);
    if (trainer != null) {
        int currentRank = trainer.getRank();
        if (currentRank > 3) {
            trainer.updateRank(currentRank - 1);
            trainerService.saveTrainer(trainer);
        }
    }
    return "redirect:/managerViewTrainers";
}

    @GetMapping("/managerViewUsers")
    public String showUsers(Model model) {
        model.addAttribute("Users", userService.getAllUsers());
        return "managerViewUsers";
    }
    @PostMapping("/removeUser/{email}")
    public String removeUser(@PathVariable("email") String email) {
        // Remove the trainer from the database
        userService.removeUser(email);
        return "redirect:/managerViewUsers";
    }
    @PostMapping("sendNotification/{email}")
    public String sendNotification(@PathVariable("email") String email) {
        // Send notification to the user
        
        return "redirect:/managerViewUsers";
    }
    

    // without session for now
    @GetMapping("/manager_appointment")
    public String listAppoingments(Model model, @SessionAttribute("manager") Manager manager) {
        // Update the status of past appointments to "inactive"
        appointmentService.deactivatePastAppointments();

        List<Appointment> allAppointment = appointmentService.findAllAppointment();
        model.addAttribute("allAppointment", allAppointment);

        return "managerViewAppointment";
    }

    @GetMapping("/manager_view_plans")
    public String managerBrowsePlans(Model model) {
        List<Plan> plans = planService.findAllPlans();
        model.addAttribute("plans", plans);
        return "managerViewPlans"; 
    }

    @GetMapping("/editPlan/{id}")
    public String editPlanForm(@PathVariable("id") int id, Model model) {
        Plan plan = planService.findPlanById(id);
        if (plan == null) {
            return "redirect:/managerViewPlans";
        }
        model.addAttribute("plan", plan);
        return "editPlan";
    }

    @PostMapping("/updatePlan/{id}")
    public String updatePlan(@PathVariable("id") int id, @ModelAttribute Plan plan, Model model) {
        planService.savePlan(plan);
        return "redirect:/manager_view_plans";
    }

}
