package com.as.controller.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.response.BaseResponse;
import com.as.service.hims.Dashboard_Service;

@RestController
@RequestMapping("/dashboard_master")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Dashboard_Controller {

	@Autowired
	Dashboard_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getCount")
	public BaseResponse getCount(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getCount(principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getRegistredCount")
	public BaseResponse getRegistredCount(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getRegistredCount(principal,yearCd);
	}
}
