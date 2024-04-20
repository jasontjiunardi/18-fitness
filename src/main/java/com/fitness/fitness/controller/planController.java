package com.fitness.fitness.controller;

import java.util.ArrayList;
import java.util.Comparator;
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
                                    @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                    @RequestParam(value = "cardholderName", required = false) String cardholderName,
                                    @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                    @RequestParam(value = "cvv", required = false) String cvv,
                                    @RequestParam(value = "expiryDate", required = false) String expiryDate,
                                    Model model) {
        if (userAgreement) {
            String transactionId = UUID.randomUUID().toString();

            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setTransactionId(transactionId);
            paymentTransaction.setPaymentMethod(paymentMethod);
            paymentTransaction.setAccountNumber(phoneNumber);
            paymentTransaction.setCardholderName(cardholderName);
            paymentTransaction.setCardNumber(cardNumber);
            paymentTransaction.setCvv(cvv);
            paymentTransaction.setExpiryDate(expiryDate);
        
            
            // Save the payment transaction to the database
            paymentTransactionRepo.save(paymentTransaction);
            
            return "redirect:/confirm_purchase"; 
        } else {
            model.addAttribute("error", "You must agree to the user agreement to proceed!");
            return "purchaseForm"; 
        }
    }
    @GetMapping("/browseChangeSubscription")
    public String changeSubscription(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            LinkedHashMap<String, Double> sortedStartingPrices = planService.getSortedPrices();
            List<String> sortedPlanTypes = new ArrayList<>(sortedStartingPrices.keySet());
            Map<String, List<Benefit>> planBenefits = planService.sortedBenefits();
            model.addAttribute("user", existingUser);
            model.addAttribute("planTypes", sortedPlanTypes);
            model.addAttribute("planBenefits", planBenefits);
        }
        
        return "changeSubscription";
    }
    @GetMapping("/changeSubscription/{planType}")
    public String changeUserPlanType(@PathVariable("planType") String planType, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Plan> plans = planService.findByPlanType(planType);
        Plan planDetails = plans.stream().findFirst().orElseThrow(() -> new RuntimeException("Plan type not found"));
    
        List<Benefit> sortedBenefits = planDetails.getBenefits().stream()
                                              .sorted(Comparator.comparing(Benefit::getDescription))
                                              .collect(Collectors.toList());
        if(user != null){
            User existingUser = userService.getUserByEmail(user.getEmail());
            Double price = planService.specificPlanStartDurationStartPrice(planType, existingUser);
            model.addAttribute("price",price);
            model.addAttribute("user", existingUser);
            model.addAttribute("planType", planType);
            model.addAttribute("planDetails", planDetails.getPlanDetails());
            model.addAttribute("benefits", sortedBenefits);
        }
        return "changeSubscriptionForm";

    }
    @PostMapping("/changeSubscription/{planType}")
    public String updateSubscription(@PathVariable("planType") String planType, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user != null){
            userService.setStatus(user.getEmail(), planType);
            model.addAttribute("userStatus",user.getStatus());
        }
        return "changeSubscriptionConfirmation";
    }
    
    

       
}

