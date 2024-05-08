package com.fitness.fitness;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.AppointmentRepo;
import com.fitness.fitness.repository.PaymentTransactionRepo;
import com.fitness.fitness.repository.ReviewRepo;
import com.fitness.fitness.repository.UserRepo;
import com.fitness.fitness.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private PaymentTransactionRepo paymentTransactionRepo;

    @Mock
    private ReviewRepo reviewRepo;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setRecoveryCode(1234);
    }

    @Test
    public void testUserLogin_ValidCredentials() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        boolean result = userService.userLogin(testUser);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testUserLogin_InvalidCredentials() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(null);

        // Act
        boolean result = userService.userLogin(testUser);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testUpdatePasswordByEmail_Success() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        userService.updatePasswordByEmail("test@example.com", "newpassword123");

        // Assert
        assertEquals("newpassword123", testUser.getPassword());
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        when(userRepo.save(any(User.class))).thenReturn(testUser);

        // Act
        User savedUser = userService.saveUser(testUser);

        // Assert
        assertEquals(testUser, savedUser);
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    public void testRemoveUser() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        userService.removeUser("test@example.com");

        // Assert
        verify(userRepo, times(1)).findByEmail("test@example.com");
        verify(appointmentRepo, times(1)).deleteAppointmentByUserId(anyInt());
        verify(paymentTransactionRepo, times(1)).deletePaymentTransactionByUserId(anyInt());
        verify(reviewRepo, times(1)).deleteReviewByUserId(anyInt());
        verify(userRepo, times(1)).delete(testUser);
    }

    @Test
    public void testResetPassword_Success() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        boolean result = userService.resetPassword("test@example.com", "password123", "newpassword123");

        // Assert
        assertTrue(result);
        assertEquals("newpassword123", testUser.getPassword());
        verify(userRepo, times(1)).save(testUser);
    }

    @Test
    public void testResetPassword_Failure() {
        // Arrange
        when(userRepo.findByEmail("test@example.com")).thenReturn(testUser);

        // Act
        boolean result = userService.resetPassword("test@example.com", "wrongpassword", "newpassword123");

        // Assert
        assertFalse(result);
        assertEquals("password123", testUser.getPassword()); // Password should remain unchanged
        verify(userRepo, never()).save(any(User.class)); // Verify that save method is not called
    }

    @Test
public void testContainsUppercaseLetterAndNumber() throws Exception {
    // Arrange
    UserService userService = new UserService(userRepo, appointmentRepo, paymentTransactionRepo, reviewRepo);
    Method method = UserService.class.getDeclaredMethod("containsUppercaseLetterAndNumber", String.class);
    method.setAccessible(true);

    // Act
    boolean result1 = (boolean) method.invoke(userService, "Password123");
    boolean result2 = (boolean) method.invoke(userService, "password");
    boolean result3 = (boolean) method.invoke(userService, "123456");
    boolean result4 = (boolean) method.invoke(userService, "PassWORD");

    // Assert
    assertTrue(result1); // Contains uppercase letter and number
    assertFalse(result2); // Does not contain uppercase letter
    assertFalse(result3); // Does not contain uppercase letter
    assertFalse(result4); // Does not contain number
}

}


