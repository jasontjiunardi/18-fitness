package com.fitness.fitness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.repository.PlanRepo;
import com.fitness.fitness.repository.ReviewRepo;
import com.fitness.fitness.repository.TrainerRepo;
import com.fitness.fitness.service.TrainerService;

public class TrainerTest {

    @Mock
    private TrainerRepo trainerRepo;

    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private PlanRepo planRepo;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTrainerById_ExistingTrainerId() {
        // Arrange
        int trainerId = 1;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(trainerId);
        when(trainerRepo.findById(trainerId)).thenReturn(java.util.Optional.of(expectedTrainer));

        // Act
        Trainer actualTrainer = trainerService.getTrainerById(trainerId);

        // Assert
        assertNotNull(actualTrainer);
        assertEquals(expectedTrainer, actualTrainer);
    }

    @Test
    public void testGetTrainerById_NonExistingTrainerId() {
        // Arrange
        int trainerId = 1;
        when(trainerRepo.findById(trainerId)).thenReturn(java.util.Optional.empty());

        // Act
        Trainer actualTrainer = trainerService.getTrainerById(trainerId);

        // Assert
        assertNull(actualTrainer);
    }

    @Test
    public void testFindAllByRank() {
        // Arrange
        int rank = 3;
        List<Trainer> expectedTrainers = List.of(new Trainer(), new Trainer());
        when(trainerRepo.findAllByRank(rank)).thenReturn(expectedTrainers);

        // Act
        List<Trainer> actualTrainers = trainerService.findAllByRank(rank);

        // Assert
        assertEquals(expectedTrainers.size(), actualTrainers.size());
        assertEquals(expectedTrainers, actualTrainers);
    }

    @Test
    public void testSaveTrainer() {
        // Arrange
        Trainer trainerToSave = new Trainer();

        // Act
        trainerService.saveTrainer(trainerToSave);

        // Assert
        verify(trainerRepo, times(1)).save(trainerToSave);
    }

    @Test
    public void testUpdateTrainer() {
        // Arrange
        Trainer trainerToUpdate = new Trainer();

        // Act
        trainerService.updateTrainer(trainerToUpdate);

        // Assert
        verify(trainerRepo, times(1)).save(trainerToUpdate);
    }

    @Test
    public void testRemoveTrainer() throws Exception {
        // Arrange
        int trainerId = 1;
        Trainer trainer = new Trainer();
        when(trainerRepo.findById(trainerId)).thenReturn(Optional.of(trainer));

        // Arrange - Mock behavior for findByTrainer and findByPlan in reviewRepo and appointmentRepo respectively
        when(reviewRepo.findByTrainer(trainer)).thenReturn(new ArrayList<>());
        when(appointmentRepo.findByTrainer(trainer)).thenReturn(new ArrayList<>());

        // Act
        trainerService.removeTrainer(trainerId);

        // Assert
        verify(trainerRepo, times(1)).delete(trainer);
        verify(reviewRepo, times(1)).findByTrainer(trainer);
        verify(appointmentRepo, times(1)).findByTrainer(trainer);
    }
}




