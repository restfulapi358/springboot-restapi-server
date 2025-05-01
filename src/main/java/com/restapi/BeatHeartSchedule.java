package com.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.restapi.config.ServiceParametersConfig;

@Component
public class BeatHeartSchedule {

	@Autowired
	private ServiceParametersConfig serviceParametersConfig;
	
	private WebClient webClient;
		
	@Scheduled(initialDelay = 5000, fixedRate = 60000)
    public void runTask() {
		
        System.out.println("Scheduled beat heart... on port:" + serviceParametersConfig.getWorkerPort());
        
        if(serviceParametersConfig.getWorkerId().equals("localhost")) {
        	return;
        }        
        
        if(webClient == null) {
        	 webClient= WebClient.builder().baseUrl(serviceParametersConfig.getWorkerUrl()).build();
        }
        webClient.post()
        .uri("/v3/edu/AnyConnectClient/heartbeat")
        .bodyValue(serviceParametersConfig)
        .retrieve()
        .bodyToMono(String.class)
        .doOnNext(response -> System.out.println("Heartbeat response: " + response))
        .doOnError(error -> System.err.println("Heartbeat failed: " + error.getMessage()))
        .subscribe();
        
        
    }
	
}
