// package com.fitness.fitness.configuration;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.fitness.fitness.model.Trainer;
// import com.fitness.fitness.repository.TrainerRepo;

// @Component
// public class trainerdataseeder implements CommandLineRunner {
//     private final TrainerRepo trainerRepo;

//     public trainerdataseeder(TrainerRepo trainerRepo) {
//         this.trainerRepo = trainerRepo;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (trainerRepo.count() == 0) {
//             seedTrainer();
//         }
//     }

//     private void seedTrainer() {
//         List<Trainer> trainers = new ArrayList<>();

//         // Create sample fitness classes
//         Trainer trainer1 = new Trainer(1, "Chris Bumstead", 27, "01-01-1997", "cbum@gmail.com", "188-888-888",
//                 "01-01-2024", 5, "../images/1.jpg");
//         Trainer trainer2 = new Trainer(1, "Noel Deyzel", 27, "01-01-1997", "cbum@gmail.com", "188-888-888",
//                 "01-01-2024", 5, "../images/2.jpg");
//         Trainer trainer3 = new Trainer(3, "Sam Sulek", 27, "01-01-1997", "cbum@gmail.com", "188-888-888",
//                 "01-01-2024", 4, "../images/3.jpg");

//                 // Add classes to the list
//         trainers.add(trainer1);
//         trainers.add(trainer2);
//         trainers.add(trainer3);

//         // Save all classes to the database
//         trainerRepo.saveAll(trainers);
//     }
// }
