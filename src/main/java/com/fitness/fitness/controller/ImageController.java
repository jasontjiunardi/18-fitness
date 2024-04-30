package com.fitness.fitness.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fitness.fitness.model.Trainer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    @GetMapping("/images/{imageName:.+}")
public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
    try {
        Path imagePath = Paths.get("src/main/resources/static/images/" + imageName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.notFound().build();
    }
}

    }
    
