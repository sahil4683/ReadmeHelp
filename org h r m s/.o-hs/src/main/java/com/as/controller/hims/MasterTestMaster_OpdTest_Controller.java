package com.as.controller.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.hims.MasterBillMaster_BillMaster_DTO;
import com.as.h_dto.hims.MasterTestMaster_OpdTest_DTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.MasterTestMaster_OpdTest_Service;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping("/master-test_master-opd_test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MasterTestMaster_OpdTest_Controller {
	
	@Autowired
	MasterTestMaster_OpdTest_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/save")
	public BaseResponse save(@RequestBody MasterTestMaster_OpdTest_DTO form,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
				BaseResponse response =  new ApiValidationUtility().validateAPI(form);
				if(response.getStatus() == 300) {
					return response;
				}
		return service.save(form, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/update")
	public BaseResponse update(@RequestBody MasterTestMaster_OpdTest_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response =  new ApiValidationUtility().validateAPI(form);
		if(response.getStatus() == 300) {
			return response;
		}
		return service.update(form, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/delete/{id}")
	public BaseResponse delete(@PathVariable(value = "id") long id, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response = new BaseResponse();
		if (id == 0) {
			response.setStatus(300);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Please review data submitted");
			return response;
		}
		return service.delete(id, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.findAll(principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getParticularsListByGroup/{groupName}")
	public BaseResponse getParticularsListByGroup(@PathVariable(value = "groupName") String groupName, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getParticularsListByGroup(groupName, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getParticularsListByOrganization/{organization}")
	public BaseResponse getParticularsListByOrganization(@PathVariable(value = "organization") String organization, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getParticularsListByOrganization(organization, principal);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getParticularsListByGroupAndOrganization/{group}/{organization}")
	public BaseResponse getParticularsListByGroupAndOrganization(@PathVariable(value = "organization") String organization,
			@PathVariable(value = "group") String group,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getParticularsListByGroupAndOrganization(group,organization, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/updateRate")
	public BaseResponse updateRate(@RequestBody MasterBillMaster_BillMaster_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.updateRate(form, principal);
	}
}
