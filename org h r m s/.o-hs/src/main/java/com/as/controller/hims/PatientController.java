package com.as.controller.hims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.response.BaseResponse;
import com.as.service.hims.PatientService;

@RestController
@RequestMapping(path="/patient")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getAdmittedPatiendDetails")
	public ResponseEntity<BaseResponse> getAdmittedPatiendDetails(@RequestHeader("yearCd") Long yearCd){
		BaseResponse response = patientService.getAdmittedPatiendDetails(yearCd);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getAdmittedPatiendDetailsByIpdNo/{ipdno}")
	public ResponseEntity<BaseResponse> getAdmittedPatiendDetailsByIpdNo(@PathVariable(value = "ipdno") String ipdno, @RequestHeader("yearCd") Long yearCd){
		BaseResponse response = patientService.getAdmittedPatiendDetailsByIpdNo(ipdno,yearCd);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getDischargedPatiendDetails")
	public ResponseEntity<BaseResponse> getDischargedPatiendDetails(@RequestHeader("yearCd") Long yearCd){
		BaseResponse response = patientService.getDischargedPatiendDetails(yearCd);
		return ResponseEntity.ok(response);
	}
	
}
