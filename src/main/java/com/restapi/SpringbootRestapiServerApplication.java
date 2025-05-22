package com.restapi;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.restapi.config.ServiceParametersConfig;

@SpringBootApplication
public class SpringbootRestapiServerApplication implements CommandLineRunner{

	
	@Autowired
	private ServiceParametersConfig serviceParametersConfig;
	
	private static int port =0 ;
	
	public static void main(String[] args) {
		
		try (ServerSocket socket = new ServerSocket(0)) {
			
            socket.setReuseAddress(true);
            port = socket.getLocalPort();
            socket.close();
            
            SpringApplication app = new SpringApplication(SpringbootRestapiServerApplication.class);
            Map<String, Object> props = new HashMap<>();
            props.put("server.port", port);
            app.setDefaultProperties(props);
            app.run(args);

            System.out.println("App started on dynamic port: " + port);
            
        }catch(Exception e) {
        	
        }
	}
	
	 @Override
	 public void run(String... args) {
		 
		 serviceParametersConfig.setWorkerPort(port+"");
		 serviceParametersConfig.setWorkerStatus("Active");
		 		 
	 }

}
