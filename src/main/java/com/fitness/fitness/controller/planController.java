package com.fitness.fitness.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fitness.fitness.model.Benefit;
import com.fitness.fitness.model.PaymentTransaction;
import com.fitness.fitness.model.Plan;
import com.fitness.fitness.model.PlanDurationPrice;
import com.fitness.fitness.model.User;
import com.fitness.fitness.repository.PaymentTransactionRepo;
import com.fitness.fitness.service.PlanService;
import com.fitness.fitness.service.UserService;

import jakarta.servlet.http.HttpSession;






@Controller
@SessionAttributes("user")
public class planController {

    @Autowired
    private PlanService planService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionRepo paymentTransactionRepo;
    @GetMapping("/browse_plans")
    public String browsePlans(Model model,  HttpSession session) {
        User user = (User) session.getAttribute("user");
        LinkedHashMap<String, Double> sortedStartingPrices = planService.getSortedPrices();
        List<String> sortedPlanTypes = new ArrayList<>(sortedStartingPrices.keySet());
        Map<String, List<Benefit>> planBenefits = planService.sortedBenefits();
        if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            model.addAttribute("user", existingUser);
        }
        model.addAttribute("planTypes", sortedPlanTypes);
        model.addAttribute("startingPrices", sortedStartingPrices);
        model.addAttribute("planBenefits", planBenefits);
        
        return "plans";

    }

    @GetMapping("/browsePlan/{planType}")
    public String browsePlan(@PathVariable("planType") String planType, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Plan> plans = planService.findByPlanType(planType);
        
        // Assuming each planType only has one set of details but multiple duration prices
        Plan planDetails = plans.stream().findFirst().orElseThrow(() -> new RuntimeException("Plan type not found"));
    
        List<Benefit> sortedBenefits = planDetails.getBenefits().stream()
                                              .sorted(Comparator.comparing(Benefit::getDescription))
                                              .collect(Collectors.toList());

    
       
        Set<PlanDurationPrice> sortedDurationPrices = planDetails.getPlanDurationPrices().stream()
        .sorted(Comparator.comparing(PlanDurationPrice::getPlanDuration))
        .collect(Collectors.toCollection(LinkedHashSet::new));
        if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            model.addAttribute("user", existingUser);
        }
        model.addAttribute("planType", planType);
        model.addAttribute("planDetails", planDetails.getPlanDetails());
        model.addAttribute("benefits", sortedBenefits);
        model.addAttribute("durationPrices", sortedDurationPrices);
        session.setAttribute("selectedPlanType", planType);
        return "planDetails";
    }

    @GetMapping("/browsePlan/{planType}/purchaseForm")
    public String showPurchaseForm(@PathVariable("planType") String planType,
                                   @RequestParam("duration") String duration, 
                                   @RequestParam("price") Double price, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            model.addAttribute("user", existingUser);
        }
        model.addAttribute("planType", planType);
        model.addAttribute("selectedDuration", duration);
        model.addAttribute("price", price);
        return "purchaseForm";
    }

    @GetMapping("/confirm_purchase")
    public String showPurchaseConfirmationPage(Model model) {
        return "purchaseConfirmation";
    }

    @PostMapping("/finalizePurchase")
    public String finalizePurchase(@RequestParam("userAgreement") boolean userAgreement, 
                                    @RequestParam("paymentMethod") String paymentMethod,
                                    @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                    Model model,
                                    HttpSession session) {
        if (userAgreement) {
            String transactionId = UUID.randomUUID().toString();
    
            // Retrieve logged-in user from session
            User loggedInUser = (User) session.getAttribute("user");
            if (loggedInUser == null) {
                // Handle the case where user is not logged in
                return "redirect:/login"; 
            }
            if ("active".equals(loggedInUser.getStatus())) {
                // If the user is already active, don't allow them to purchase again
                model.addAttribute("error", "You already have an active plan");
                return "purchaseForm";
            }
    
            // Get user ID from logged-in user
            int userIdInt = loggedInUser.getUserId();
            String userId = String.valueOf(userIdInt);

            // Get the selected plan type from the session
            String planType = (String) session.getAttribute("selectedPlanType");
            
            // Set the user status to active
            loggedInUser.setStatus("active");

            // Update the user status in the database
            userService.saveUser(loggedInUser);


            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setTransactionId(transactionId);
            paymentTransaction.setUserId(userId); // Set user ID
            paymentTransaction.setPlanType(planType); // Set plan type
            paymentTransaction.setPaymentMethod(paymentMethod);
            paymentTransaction.setPaymentType("buy"); // Set payment type
            
            // Set purchased date
            Date today = new Date(); // Get current date
            paymentTransaction.setPurchasedDate(today);

            // Set card number if payment method is credit card
            if ("Credit".equals(paymentMethod)) {
                loggedInUser.setCardNumber(cardNumber);
                paymentTransaction.setCardNumber(cardNumber);
                userService.saveUser(loggedInUser); // Save user with card number
            }else {
                paymentTransaction.setCardNumber("");
            }

             // Set user session attribute to prevent purchasing again
            session.setAttribute("purchased", true);

            // Save the payment transaction to the database
            paymentTransactionRepo.save(paymentTransaction);
            
            return "redirect:/confirm_purchase"; 
        } else {
            model.addAttribute("error", "You must agree to the user agreement to proceed!");
            return "purchaseForm"; 
        }
    }
    
       
}
