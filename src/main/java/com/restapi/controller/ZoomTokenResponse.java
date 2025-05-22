package com.restapi.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZoomTokenResponse {
	
	@JsonProperty("access_token")
    private String accessToken;

    // Getters and setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
