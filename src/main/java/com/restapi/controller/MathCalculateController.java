package com.restapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/math")
@CrossOrigin(allowedHeaders = "*")
public class MathCalculateController {
	
	
	@GetMapping(path = "/pi", produces="application/json")
	public ResponseEntity<?> pi() {	
		
		MathResponse response = new MathResponse();
		response.setResult("" + Math.PI);
		
		return new ResponseEntity(response, HttpStatus.OK) ;	
	}

}
