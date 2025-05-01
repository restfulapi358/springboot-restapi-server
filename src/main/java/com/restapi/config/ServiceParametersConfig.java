package com.restapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ServiceParametersConfig {
	
	 @Value("${key:localtest}")
	 private String key;
	 
	 @Value("${workerId:localhost}")
	 private String workerId;
	 
	 @Value("${workerUrl:localhost:8080}")
	 private String workerUrl;	 
	 
	 @Value("${workerHost:localhost}")
	 private String workerHost;
	 
	 @Value("${workerName:apiservice}")
	 private String workerName;
	 
	 @Value("${workerVersion:1.0}")
	 private String workerVersion;
	 
	 @Value("${workerPort:0}")
	 private String workerPort;
	
	 @Value("${workerRoute:route}")
	 private String workerRoute;
	
	 
	 @Value("${workerRouteVersion:00100}")
	 private String workerRouteVersion;
	 
	 private String workerStatus;
	 
}
