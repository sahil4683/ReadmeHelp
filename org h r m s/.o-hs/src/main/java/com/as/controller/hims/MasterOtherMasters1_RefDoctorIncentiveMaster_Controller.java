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

import com.as.h_dto.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_DTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Service;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping("/master-other_masters1-ref_doctor_incentive_master")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MasterOtherMasters1_RefDoctorIncentiveMaster_Controller {

	@Autowired
	MasterOtherMasters1_RefDoctorIncentiveMaster_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/save")
	public BaseResponse save(@RequestBody MasterOtherMasters1_RefDoctorIncentiveMaster_DTO form,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
				BaseResponse response =  new ApiValidationUtility().validateAPI(form);
				if(response.getStatus() == 300) {
					return response;
				}
		return service.save(form, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/update")
	public BaseResponse update(@RequestBody MasterOtherMasters1_RefDoctorIncentiveMaster_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
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
	
}
