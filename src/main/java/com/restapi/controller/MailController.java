package com.restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.controller.service.MailService;

@RestController
@RequestMapping("/v1/mail")
@CrossOrigin(allowedHeaders = "*")
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@GetMapping("/send-email")
	public String sendEmail() {
	    mailService.sendSimpleEmail("jianmin.dai.it@gmail.com", "Test Subject", "Test Body");
	    return "Email sent successfully";
	}

	@GetMapping("/send-template-email")
	public String sendTemplateEmail() {
		
		Map<String, Object> model = new HashMap<>();
		model.put("name", "John Doe");
		model.put("link", "https://yourwebsite.com/reset-password?token=abc123");

		try {
			mailService.sendTemplateEmail("jianmin.dai.it2@gmail.com", "Reset Your Password", model, "email-template");
			return "Email sent successfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Email sent Failed";
	}
	
	

}
