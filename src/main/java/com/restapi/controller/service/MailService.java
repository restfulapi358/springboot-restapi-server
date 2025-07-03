package com.restapi.controller.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

import java.util.Map;


@Service
public class MailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
    	
        SimpleMailMessage message = new SimpleMailMessage();        
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        
    }
    
    
    public void sendTemplateEmail(String to, String subject, Map<String, Object> model, String template) throws Exception {
    	
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(model);

        String html = templateEngine.process(template, context);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        
        mailSender.send(message);
    }

}
