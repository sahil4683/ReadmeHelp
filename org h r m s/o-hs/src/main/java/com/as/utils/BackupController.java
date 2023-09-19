//package com.as.utils;
//
//import java.security.Principal;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.as.response.BaseResponse;
//import com.as.response.ResponseType;
//
//@RestController
//@RequestMapping("/Backup")
//@CrossOrigin(origins = "*", maxAge = 3600)
//public class BackupController {
//
//	@Value("${backup.database.name}")
//	private String name;
//	
//	@Value("${spring.datasource.username}")
//	private String user;
//	
//	@Value("${spring.datasource.password}")
//	private String pass;
//	
//	@Autowired
//	BackupService backupService;
//	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
//	@GetMapping("/GenerateBackup")
//	public BaseResponse GenerateBackup(@RequestHeader("villageCd") String villageCd,
//			Principal principal) {
//		BaseResponse response = new BaseResponse();
//		
//		backupService.generate(name,user,pass);
//		
//		response.setStatus(200);
//		response.setType(ResponseType.SUCCESS);
//		response.setMessage("Backup Generated");
//		
//		return response;
//	}
//	
//}
