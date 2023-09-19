//package com.as.swagger;
//
//import java.util.Arrays;
//import java.util.HashSet;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.common.collect.Lists;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//	Contact contact = new Contact(
//			"Alligate System", 
//			"", 
//			"");
//	
//
//	ApiInfo apiInfo = new ApiInfo("Orange Hospital Management System - API`s", "", "V1.0.0", "", contact, "", "");
//
//	@Bean
//	public Docket apiDocket() {
//		Docket docket = new Docket(DocumentationType.SWAGGER_2)
//				.protocols(new HashSet<>(Arrays.asList("HTTP","HTTPS")))
//				.apiInfo(apiInfo)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.as"))
//				.paths(PathSelectors.any())
//				.build().securitySchemes(Lists.newArrayList(apiKey()));
//		
//		return docket;
//	}
//	
//
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }
//
//}