package com.fitness.fitness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.repository.PlanRepo;
import com.fitness.fitness.repository.PlanDurationPriceRepo;
import com.fitness.fitness.service.PlanService;

@SpringBootTest
public class PlanTest {

    @Mock
    private PlanRepo planRepo;

    @Mock
    private PlanDurationPriceRepo planDurationPriceRepo;

    @InjectMocks
    private PlanService planService;

    @Test
    public void sortedBenefits_NotEmpty() {
        // Arrange
        LinkedHashMap<String, List<Benefit>> mockSortedBenefits = new LinkedHashMap<>();
        mockSortedBenefits.put("Gold", Arrays.asList(new Benefit("Benefit 1"), new Benefit("Benefit 2")));
        mockSortedBenefits.put("Silver", Arrays.asList(new Benefit("Benefit 3"), new Benefit("Benefit 4")));
    
        PlanService planServiceSpy = spy(planService);
        doReturn(mockSortedBenefits).when(planServiceSpy).sortedBenefits();
    
        // Act
        Map<String, List<Benefit>> sortedBenefits = planServiceSpy.sortedBenefits();
    
        // Assert
        assertFalse(sortedBenefits.isEmpty());
        assertEquals(mockSortedBenefits.size(), sortedBenefits.size());
    }



    @Test
    public void findAllPlans_NotEmpty() {
        // Arrange
        List<Plan> plans = Arrays.asList(new Plan(), new Plan()); // Mock list of plans
        when(planRepo.findAll()).thenReturn(plans);

        // Act
        List<Plan> foundPlans = planService.findAllPlans();

        // Assert
        assertFalse(foundPlans.isEmpty());
        assertEquals(plans.size(), foundPlans.size());
    }

    @Test
    public void findPlanById_ValidId() {
        // Arrange
        int planId = 1;
        Plan mockPlan = new Plan();
        mockPlan.setId(planId);
        when(planRepo.findById(planId)).thenReturn(Optional.of(mockPlan));

        // Act
        Plan foundPlan = planService.findPlanById(planId);

        // Assert
        assertNotNull(foundPlan);
        assertEquals(mockPlan.getId(), foundPlan.getId());
    }

   
}

