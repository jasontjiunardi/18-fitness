package com.fitness.fitness.configuration;

import com.fitness.fitness.model.FitnessClass;
import com.fitness.fitness.repository.FitnessClassRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FitnessClassDataSeeder implements CommandLineRunner {

    private final FitnessClassRepo fitnessClassRepo;

    public FitnessClassDataSeeder(FitnessClassRepo fitnessClassRepo) {
        this.fitnessClassRepo = fitnessClassRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (fitnessClassRepo.count() == 0) {
            seedFitnessClasses();
        }
    }

    private void seedFitnessClasses() {
        List<FitnessClass> classes = new ArrayList<>();

        // Create sample fitness classes
        FitnessClass class1 = new FitnessClass("Yoga",
                "Relaxing yoga session that focuses on meditation, relaxation and sense of relief", "1 hour",
                "https://tse4.explicit.bing.net/th?id=OIP.tS11V4H7wOaGd0XVYWa74AHaE8&pid=Api&P=0&h=220");
        FitnessClass class2 = new FitnessClass("Zumba",
                "High-energy dance workout that focuses on rhythmic movements with upbeat music", "45 minutes",
                "https://tse4.mm.bing.net/th?id=OIP.-0tX91NoRvRXPEB2mE0bpQHaE9&pid=Api&P=0&h=220");
        FitnessClass class3 = new FitnessClass("Pilates", "Core-strengthening exercises that focuses abs and mobility",
                "50 minutes", "https://tse3.mm.bing.net/th?id=OIP.LrYv0dvdzYQDKd2uTAXSzwHaE8&pid=Api&P=0&h=220");
        FitnessClass class4 = new FitnessClass("HIIT",
                "this is a very intense exercise that aims to burn fat and cardio", "60 min", "https://tse3.mm.bing.net/th?id=OIP.1EXqVa1_08wo6iLTHJZUrQHaEK&pid=Api&P=0&h=220");

        // Add classes to the list
        classes.add(class1);
        classes.add(class2);
        classes.add(class3);
        classes.add(class4);

        // Save all classes to the database
        fitnessClassRepo.saveAll(classes);
    }

}
