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
//         Trainer trainer1 = new Trainer(1, "Chris Bumstead", 27, "Male", "1997-01-01", "cbum@gmail.com", "trainer1.jpg", "188-888-888", 5, "2024-01-01");
//         Trainer trainer2 = new Trainer(2, "Noel Deyzel", 27, "Male", "1997-01-01", "cbum@gmail.com", "trainer2.jpg", "188-888-888", 4, "2024-01-01");
//         Trainer trainer3 = new Trainer(3, "Sam Sulek", 27, "Male", "1997-01-01", "cbum@gmail.com", "trainer3.jpg", "188-888-888", 3, "2024-01-01");
//         Trainer trainer4 = new Trainer(4, "Jeff Nippard", 27, "Male", "1997-01-01", "cbum@gmail.com", "trainer4.jpg", "188-888-888", 5, "2024-01-01");

//         // Add classes to the list
//         trainers.add(trainer1);
//         trainers.add(trainer2);
//         trainers.add(trainer3);
//         trainers.add(trainer4);

//         // Save all classes to the database
//         trainerRepo.saveAll(trainers);
//     }
// }
