package com.fitness.fitness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.fitness.fitness.controller.ManagerController;
import com.fitness.fitness.model.Expense;
import com.fitness.fitness.model.Income;
import com.fitness.fitness.model.Manager;
import com.fitness.fitness.model.Trainer;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.ExpenseRepo;
import com.fitness.fitness.repository.IncomeRepo;
import com.fitness.fitness.repository.ManagerRepo;
import com.fitness.fitness.service.EmailService;
import com.fitness.fitness.service.ManagerService;
import com.fitness.fitness.service.TrainerService;
import com.fitness.fitness.service.UserService;

@SpringBootTest
public class ManagerTest {

    private ManagerController managerController;
    private TrainerService trainerService;
    private EmailService emailService;
    private IncomeRepo incomeRepo;
    private ExpenseRepo expenseRepo;
    private UserService userService;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        managerController = new ManagerController();
        trainerService = mock(TrainerService.class);
        emailService = mock(EmailService.class);
        incomeRepo = mock(IncomeRepo.class);
        expenseRepo = mock(ExpenseRepo.class);
        userService = mock(UserService.class);

        // Use reflection to set the private fields
        setPrivateField(managerController, "trainerService", trainerService);
        setPrivateField(managerController, "emailService", emailService);
        setPrivateField(managerController, "incomeRepo", incomeRepo);
        setPrivateField(managerController, "expenseRepo", expenseRepo);
        setPrivateField(managerController, "userService", userService);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    public void testManagerLogin_Success() {
        // Arrange
        ManagerRepo managerRepo = mock(ManagerRepo.class);
        ManagerService managerService = new ManagerService(managerRepo);
        Manager manager = new Manager();
        manager.setEmail("test@example.com");
        manager.setPassword("password123");

        when(managerRepo.findByEmail(manager.getEmail())).thenReturn(manager);

        // Act
        boolean result = managerService.managerLogin(manager);

        // Assert
        assertEquals(true, result);
    }

    @Test
    public void testManagerLogin_Failure() {
        // Arrange
        ManagerRepo managerRepo = mock(ManagerRepo.class);
        ManagerService managerService = new ManagerService(managerRepo);
        Manager manager = new Manager();
        manager.setEmail("test@example.com");
        manager.setPassword("wrongpassword");

        when(managerRepo.findByEmail(manager.getEmail())).thenReturn(null);

        // Act
        boolean result = managerService.managerLogin(manager);

        // Assert
        assertEquals(false, result);
    }

    @Test
    public void testPromoteTrainer() {
        // Arrange
        int trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setRank(3); // Assuming initial rank is 3
        when(trainerService.getTrainerById(trainerId)).thenReturn(trainer);

        // Act
        String result = managerController.promoteTrainer(trainerId);

        // Assert
        assertEquals("redirect:/managerViewTrainers", result);
        verify(trainerService).saveTrainer(trainer); // Verify that saveTrainer was called
    }

    @Test
    public void testDemoteTrainer() {
        // Arrange
        int trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setRank(4); // Assuming initial rank is 4
        when(trainerService.getTrainerById(trainerId)).thenReturn(trainer);

        // Act
        String result = managerController.demoteTrainer(trainerId);

        // Assert
        assertEquals("redirect:/managerViewTrainers", result);
        trainer.setRank(3); // Expecting the rank to be demoted
        verify(trainerService).saveTrainer(trainer); // Verify that saveTrainer was called
    }

    

    @Test
    public void testSendEmail_Success() {
        // Arrange
        String email = "test@example.com";
        String name = "Test User";
        LocalDate activeDate = LocalDate.now().plusDays(10);
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setActiveDate(activeDate);

        when(userService.getUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<String> response = managerController.sendEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"message\": \"Email sent successfully to " + email + "\"}", response.getBody());
        verify(emailService).sendMembershipReminderEmail(email, name, activeDate); // Verify that sendMembershipReminderEmail was called
    }

    @Test
    public void testSendEmail_Failure() {
        // Arrange
        String email = "test@example.com";

        when(userService.getUserByEmail(email)).thenThrow(new RuntimeException("User not found"));

        // Act
        ResponseEntity<String> response = managerController.sendEmail(email);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("{\"message\": \"Failed to send email due to an error\"}", response.getBody());
    }

    
}
