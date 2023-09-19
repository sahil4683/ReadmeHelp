package com.as.controller.patho;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.patho.Patho_EnterData_DTO;
import com.as.request.SaveTestDataDTO;
import com.as.response.BaseResponse;
import com.as.response.PathoWatingPatientListResponse;
import com.as.service.patho.Patho_PathoLabReport_Service;

@RestController
@RequestMapping("/patho_lab_report")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Patho_PathoLabReport_Controller {
	
	@Autowired
	Patho_PathoLabReport_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getCountByDate/{date}")
	public BaseResponse getCountByDate(
			@PathVariable(value = "date") String date,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getCountByDate(date, principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getWaitingPatientByDate/{date}")
	public BaseResponse getWaitingPatientByDate(
			@PathVariable(value = "date") String date, 
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getWaitingPatientByDate(date, principal,yearCd);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
//	@GetMapping("/waiting/{uhid}/{ipdno}/{dept}/{date}/{billNo}")
//	public BaseResponse waiting(
//			@PathVariable(value = "uhid") Long uhid, 
//			@PathVariable(value = "ipdno") Long ipdno, 
//			@PathVariable(value = "dept") String dept, 
//			@PathVariable(value = "date") String date,
//			@PathVariable(value = "billNo") String billNo,
//			Principal principal, @RequestHeader("yearCd") Long yearCd) {
//		return service.waitingRequest(uhid, ipdno, dept, date, billNo, principal);
//	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/waiting")
	public BaseResponse waiting(
			@RequestBody PathoWatingPatientListResponse item,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.waitingRequest(item, principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/saveTestData")
	public BaseResponse saveTestData(
			@RequestBody List<SaveTestDataDTO> form,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.saveTestData(form, principal,yearCd);
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getOtherByDateTableAndType/{date}/{type}")
	public BaseResponse getOtherByDateTableAndType(
			@PathVariable(value = "date") String date,
			@PathVariable(value = "type") String type, 
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getOtherByDateTableAndType(date, type, principal,yearCd);
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/collected/{id}")
	public BaseResponse collected(
			@PathVariable(value = "id") Long id,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.collectedRequest(id,principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/updateTime/{id}")
	public BaseResponse updateTime(
			@PathVariable(value = "id") Long id,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.updateTime(id,principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/verifyTestAll/{id}")
	public BaseResponse verifyTestAll(
			@PathVariable(value = "id") Long id,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.verifyTestAll(id,principal,yearCd);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/receivedRequest/{id}")
	public BaseResponse receivedRequest(
			@PathVariable(value = "id") String id,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.receivedRequest(id,principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/saveObservationTest")
	public BaseResponse saveObservationTest(@RequestBody List<Patho_EnterData_DTO> forms, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.saveObservationTest(forms, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/printReport/{id}/{type}/{format}")
	@ResponseBody
	public HttpEntity<byte[]> printReport(
			@PathVariable(value = "id") String id,
			@PathVariable(value = "type") String type,
			@PathVariable(value =  "format") String format,
			Principal principal,
			@RequestHeader("yearCd") Long yearCd
			) {
			final byte[] data = service.report_PDF(id,type,format,principal,yearCd);
			HttpHeaders header = new HttpHeaders();
			if(format.equals("pdf")){ header.setContentType(MediaType.APPLICATION_PDF); }
			if(format.equals("xlsx")) { header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); }
			String headerValue = "attachment; filename=Report_File("+new Timestamp(System.currentTimeMillis()) +")."+format;
			header.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
			if(data !=null) { header.setContentLength(data.length); }
			return new HttpEntity<byte[]>(data, header);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/verifyTest/{id}/{formatName}")
	public BaseResponse verifyTest(
			@PathVariable(value = "id") String id,
			@PathVariable(value = "formatName") String formatName,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.verifyTest(id,formatName,principal,yearCd);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.findAll(principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getByDate/{date}")
	public BaseResponse getCurrent(@PathVariable(value = "date") String date,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getCurrent(date,principal,yearCd);
	}
	
}
