package com.fitness.fitness.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;


@Service
public class EmailService {
    @Autowired
    private  JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;;
   
    public void sendMembershipReminderEmail(String to, String name, LocalDate expirationDate){
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("expirationDate", expirationDate);
        
        String processHtml = templateEngine.process("membershipReminder", context);
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setFrom("daniellimotto27@gmail.com");
            helper.setTo(to);
            helper.setSubject("Your Membership is Expiring Soon!");
            helper.setText(processHtml, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
}
