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
import com.as.service.hims.MasterOtherMasters1_AccountGroup_Service;

@RestController
@RequestMapping("/master-other_masters1-account_group")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MasterOtherMasters1_AccountGroup_Controller {

	@Autowired
	MasterOtherMasters1_AccountGroup_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.findAll(principal);
	}
}
