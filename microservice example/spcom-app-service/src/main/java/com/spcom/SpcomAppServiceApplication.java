package com.spcom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class SpcomAppServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpcomAppServiceApplication.class, args);
	}
	
	@GetMapping(value = "hello")
	public String getHello(@RequestParam String param) {
		return "Hello From App Service";
	}


}
