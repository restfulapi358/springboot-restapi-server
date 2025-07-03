package com.restapi.controller;

import java.util.Map;

import lombok.Data;

@Data
public class TemplateEmailRequest {
	
	private String to;
	private String subject;
	private String template;
	
	private Map<String,Object> model;
	
	
}
