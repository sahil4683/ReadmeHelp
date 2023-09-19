package com.as.controller.patho;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.patho.Patho_FooterUpdation_DTO;
import com.as.response.BaseResponse;
import com.as.service.patho.Patho_FooterUpdation_Service;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping("/patho_footer_updation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Patho_FooterUpdation_Controller {
	
	@Autowired
	Patho_FooterUpdation_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/update")
	public BaseResponse update(@RequestBody Patho_FooterUpdation_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response =  new ApiValidationUtility().validateAPI(form);
		if(response.getStatus() == 300) {
			return response;
		}
		return service.update(form, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.findAll(principal);
	}
	
}
