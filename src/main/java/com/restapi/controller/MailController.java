package com.restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public String sendEmail(@RequestBody SimpleEmailRequest request) {
	    mailService.sendSimpleEmail(request.getTo(),request.getSubject(), request.getText());
	    return "Email sent successfully";
	}

	@GetMapping("/send-template-email")
	public String sendTemplateEmail(@RequestBody TemplateEmailRequest request) {
		
		try {
			mailService.sendTemplateEmail(request.getTo(), request.getSubject(), request.getModel(), request.getTemplate());
			return "Email sent successfully";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Email sent Failed";
	}
	
	

}
