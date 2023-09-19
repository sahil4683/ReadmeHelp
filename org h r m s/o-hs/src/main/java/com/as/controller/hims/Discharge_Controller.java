package com.as.controller.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.hims.Discharge_DTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.DischargeService;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping(value="/discharge")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Discharge_Controller {

//	private static final Logger logger = Logger.getLogger(Discharge_Controller.class.getName());
	
	@Autowired
	private DischargeService service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/saveOrUpdate")
	public BaseResponse saveOrUpdate(@RequestBody Discharge_DTO form,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		
//		logger.info("Enter into Discharge_Controller saveOrUpdate(@RequestBody Discharge_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd)");
		
BaseResponse response =  new ApiValidationUtility().validateAPI(form);
if(response.getStatus() == 300) {
	return response;
}
		return service.saveOrUpdate(form, principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
//		logger.info("Enter into Discharge_Controller findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) ");
		
		return service.findAll(principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getByDate/{date}")
	public BaseResponse getCurrent(@PathVariable(value = "date") String date,Principal principal, @RequestHeader("yearCd") Long yearCd) {
//		logger.info("Enter into Discharge_Controller getCurrent(@PathVariable(value = \"date\") String date,Principal principal, @RequestHeader("yearCd") Long yearCd)");
		
		return service.getCurrent(date,principal,yearCd);
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
}
