package com.as;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@RestController
public class OrangeHospitalServiceApplication extends SpringBootServletInitializer implements ErrorController {

	
	public static void main(String[] args) {
		SpringApplication.run(OrangeHospitalServiceApplication.class, args);
	}

	@GetMapping("/status")
	public String hello() {
		return "<h1 style='color:#19bb34;font-size:80px;text-align:center;'>Yep.. Server Running!<h1>";
	}
	
	@GetMapping("/error")
    public String handleError() {
        return "<h1 style='color:#fb0505;font-size:80px;text-align:center;'>Something went wrong!<h1>";
    }

	@Override
	public String getErrorPath() {
		System.out.println("Requested Url Not Found !");
		return null;
	}

}
