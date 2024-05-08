package com.fitness.fitness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fitness.fitness.model.Appointment;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.service.AppointmentService;
import com.fitness.fitness.service.PlanService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;

// Import your service classes and model classes here
@SpringBootTest
public class AppointmentTest {

    @Mock
    private AppointmentRepo appointmentRepo;

    // Mocking service classes
    UserService userService = mock(UserService.class);
    TrainerService trainerService = mock(TrainerService.class);
    PlanService planService = mock(PlanService.class);
    AppointmentService appointmentService = mock(AppointmentService.class);

    // Mocking model classes
    User mockUser = new User();
    Trainer mockTrainer = new Trainer();
    Plan mockPlan = new Plan();

    @Test
    public void testGetUserByEmail_ValidEmail() {
        // Arrange
        String userEmail = "user@example.com";
        mockUser.setEmail(userEmail);
        when(userService.getUserByEmail(userEmail)).thenReturn(mockUser);

        // Act
        User actualUser = userService.getUserByEmail(userEmail);

        // Assert
        assertEquals(mockUser, actualUser);
    }

    @Test
    public void testGetTrainersByPlanId_ValidPlanId() {
        // Arrange
        int planId = 123;
        List<Trainer> expectedTrainers = new ArrayList<>();
        // Populate expectedTrainers with mock Trainer objects
        when(trainerService.getTrainersByPlanId(planId)).thenReturn(expectedTrainers);

        // Act
        List<Trainer> actualTrainers = trainerService.getTrainersByPlanId(planId);

        // Assert
        assertEquals(expectedTrainers, actualTrainers);
    }

    @Test
    public void testFindByPlanType_ValidPlanType() {
        // Arrange
        String planType = "Diamond";
        mockPlan.setPlanType(planType);
        when(planService.findByPlanType(planType)).thenReturn(mockPlan);

        // Act
        Plan actualPlan = planService.findByPlanType(planType);

        // Assert
        assertEquals(mockPlan, actualPlan);
    }

    @Test
    public void testGetAppointmentIdsByUserId_ValidUserId() {
        // Arrange
        int userId = 123;
        List<Integer> expectedAppointmentIds = Arrays.asList(1, 2, 3); // Example appointment IDs
        when(appointmentService.getAppointmentIdsByUserId(userId)).thenReturn(expectedAppointmentIds);

        // Act
        List<Integer> actualAppointmentIds = appointmentService.getAppointmentIdsByUserId(userId);

        // Assert
        assertEquals(expectedAppointmentIds, actualAppointmentIds);
    }

    @Test
    public void testDeactivatePastAppointments() {
        // Create mock appointments with past dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        Appointment pastAppointment1 = new Appointment();
        pastAppointment1.setAppointmentId(1);
        pastAppointment1.setDate(currentDateTime.minusDays(1)); // Past date
        Appointment pastAppointment2 = new Appointment();
        pastAppointment2.setAppointmentId(2);
        pastAppointment2.setDate(currentDateTime.minusHours(2)); // Past date

        // Mock the AppointmentRepo
        AppointmentRepo appointmentRepo = mock(AppointmentRepo.class);
        when(appointmentRepo.updateStatusForPastAppointments(any(LocalDateTime.class)))
                .thenReturn(2); // Assuming 2 appointments were deactivated

        // Create an instance of AppointmentService with the mocked AppointmentRepo
        AppointmentService appointmentService = new AppointmentService(appointmentRepo);

        // Call the method under test
        int deactivatedCount = appointmentService.deactivatePastAppointments();

        // Assert that the correct number of appointments were deactivated
        assertEquals(2, deactivatedCount);
    }
    

}
