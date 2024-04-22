package com.fitness.fitness.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        Plan planDetails = planService.findByPlanType(planType);
    
        if (planDetails == null) {
            throw new RuntimeException("Plan type not found");
        }
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
                                    @RequestParam("planType") String planType,
                                    @RequestParam("duration") String duration,
                                    @RequestParam("price") Double price, 
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
                
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            if(loggedInUser.getStatus().equals(planType)){
                long durationChosen = Integer.parseInt(duration.split(" ")[0]);
                LocalDate newActiveDate = loggedInUser.getActiveDate().plusMonths(durationChosen);
                loggedInUser.setActiveDate(newActiveDate);
                paymentTransaction.setDuration((double) durationChosen);
                paymentTransaction.setActiveDate(newActiveDate);
                if(loggedInUser.getCardNumber()==null && cardNumber != null){
                    loggedInUser.setCardNumber(cardNumber);
                }
            }
            else{
                loggedInUser.setStatus(planType);
                long durationChosen = Integer.parseInt(duration.split(" ")[0]);
                LocalDate now = LocalDate.now().plusMonths(durationChosen);
                loggedInUser.setActiveDate(now);
                paymentTransaction.setDuration((double) durationChosen);
                paymentTransaction.setActiveDate(now);
                if(loggedInUser.getCardNumber()==null && cardNumber != null){
                    loggedInUser.setCardNumber(cardNumber);
                }
            }
            
            userService.saveUser(loggedInUser);


            paymentTransaction.setTransactionId(transactionId);
            paymentTransaction.setUser(loggedInUser);
            paymentTransaction.setPlanType(planType); // Set plan type
            paymentTransaction.setPaymentMethod(paymentMethod);
            paymentTransaction.setPaymentType("Buy"); // Set payment type
            paymentTransaction.setPrice(price);
            paymentTransaction.setPlan(planService.findByPlanType(planType));
            // Set purchased date
            LocalDate today = LocalDate.now(); // Get current date
            paymentTransaction.setPurchasedDate(today);

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
        Plan planDetails = planService.findByPlanType(planType);
    
        if (planDetails == null) {
            throw new RuntimeException("Plan type not found");
        }
    
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
    public String updateSubscription(@PathVariable("planType") String planType, Model model, HttpSession session,
                                    @RequestParam("paymentMethod") String paymentMethod,
                                    @RequestParam(value = "cardNumber", required = false) String cardNumber,
                                    @RequestParam("price") Double price) {
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser != null){
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            loggedInUser.setStatus(planType);
            paymentTransaction.setActiveDate(loggedInUser.getActiveDate());
            if(loggedInUser.getCardNumber()==null && cardNumber != null){
                loggedInUser.setCardNumber(cardNumber);
            }
            userService.saveUser(loggedInUser);
            LocalDate today = LocalDate.now();
            LocalDate activeDate = loggedInUser.getActiveDate();
            Double remainingMonths = (double) ChronoUnit.MONTHS.between(today, activeDate);
            String transactionId = UUID.randomUUID().toString();
            paymentTransaction.setTransactionId(transactionId);
            paymentTransaction.setUser(loggedInUser); 
            paymentTransaction.setPlanType(planType); 
            paymentTransaction.setPaymentMethod(paymentMethod);
            paymentTransaction.setPrice(price);
            paymentTransaction.setDuration(remainingMonths);
            paymentTransaction.setPlan(planService.findByPlanType(planType));
            if(price>0.0){
                paymentTransaction.setPaymentType("Upgrade");
            }
            else{           
                paymentTransaction.setPaymentType("Downgrade");}
            paymentTransaction.setPurchasedDate(today);

            // Save the payment transaction to the database
            paymentTransactionRepo.save(paymentTransaction);
            
            return "changeSubscriptionConfirmation"; 
        }
        return "changeSubscriptionForm";
    }
    
    

       
}

