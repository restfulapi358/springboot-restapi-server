package com.restapi.controller;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/zoom")
@CrossOrigin(allowedHeaders = "*")
public class ZoomController {

	private String clientId = "kGxTLy7xSmaIbR0d86zJQ";
	private String clientSecret = "PLT71vsLbkNjTUbk3MaRCKlUsCMOv3u7";
	private String accountId = "7FqEKzdDRXmci4QWPlSniA";
	private String token = "eyJzdiI6IjAwMDAwMiIsImFsZyI6IkhTNTEyIiwidiI6IjIuMCIsImtpZCI6ImE2MjE5NTRjLWI0ZjItNGUzYS1hYjQ3LWEwY2UzNWRmMDc2NCJ9.eyJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJKUWJsaGp1Y1FHMk9fVFVWRVYtbVlnIiwidmVyIjoxMCwiYXVpZCI6ImE1NDk2ZjU5MGVjNjI2MGFiYTlhZTYzNTAzZTdkMGZlNzRkYjE2NTU1YzZjMmYxNDJjMzI5N2QzYzJhMDZkMmQiLCJuYmYiOjE3NDc4NjYxOTQsImNvZGUiOiJGbnNDeDBocFNfV3E4UGFQMnhzUEhncnlYUG5uRWtPTHgiLCJpc3MiOiJ6bTpjaWQ6a0d4VEx5N3hTbWFJYlIwZDg2ekpRIiwiZ25vIjowLCJleHAiOjE3NDc4Njk3OTQsInR5cGUiOjMsImlhdCI6MTc0Nzg2NjE5NCwiYWlkIjoiN0ZxRUt6ZERSWG1jaTRRV1BsU25pQSJ9.3yT91FTBWfrKYTNDwAMTGwuQJp8JH-lXeZz7igcGYtH_EuJ9AMRVP6EqDuFkzjrukhbQfipPNz0u5pabw_VpHg";
		
	@GetMapping("/token")
    public Mono<ResponseEntity<String>> getAccessToken() {
		
		String auth = Base64.getEncoder()
	            .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

	    WebClient client = WebClient.builder()
	            .baseUrl("https://zoom.us")
	            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth)
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	            .build();

	    return client.post()
	            .uri(uriBuilder -> uriBuilder.path("/oauth/token")
	                    .queryParam("grant_type", "account_credentials")
	                    .queryParam("account_id", accountId)
	                    .build())
	            .retrieve()
	            .toEntity(String.class);
	}
	
	@PostMapping("/meetings")
    public Mono<ResponseEntity<String>> meetings(@RequestBody ZoomMeetingRequest request) {
		
		WebClient  webClient = WebClient.create("https://api.zoom.us/v2");
        return webClient.post()
                .uri("/users/"+ "hustacdjm@gmail.com" + "/meetings")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(request)
                .retrieve()
                .toEntity(String.class);
    }
	
	
	@PostMapping("/createMeeting")
	public ResponseEntity<String> createMeeting(@RequestBody ZoomMeetingRequest request) {

	    // Step 1: Get Access Token from Zoom OAuth
	    String auth = Base64.getEncoder()
	            .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

	    WebClient tokenClient = WebClient.builder()
	            .baseUrl("https://zoom.us")
	            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth)
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	            .build();

	    ZoomTokenResponse tokenResponse = tokenClient.post()
	            .uri(uriBuilder -> uriBuilder.path("/oauth/token")
	                    .queryParam("grant_type", "account_credentials")
	                    .queryParam("account_id", accountId)
	                    .build())
	            .retrieve()
	            .bodyToMono(ZoomTokenResponse.class)
	            .block();

	    if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to get access token");
	    }

	    // Step 2: Create Meeting with Access Token
	    String accessToken = tokenResponse.getAccessToken();

	    WebClient webClient = WebClient.create("https://api.zoom.us/v2");

	    return webClient.post()
	            .uri("/users/" + "hustacdjm@gmail.com" + "/meetings")
	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	            .bodyValue(request)
	            .retrieve()
	            .toEntity(String.class)
	            .block();
	}
}
