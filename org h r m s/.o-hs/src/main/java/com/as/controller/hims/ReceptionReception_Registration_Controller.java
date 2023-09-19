package com.as.controller.hims;

import java.security.Principal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.hims.ReceptionReception_Registration_DTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.ReceptionReceptionRegistration_Service;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping("/reception_reception_registration")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReceptionReception_Registration_Controller {
	
	@Autowired
	ReceptionReceptionRegistration_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/save")
	public BaseResponse save(@RequestBody ReceptionReception_Registration_DTO form,
			Principal principal, @RequestHeader("yearCd") Long yearCd) {
				BaseResponse response =  new ApiValidationUtility().validateAPI(form);
				if(response.getStatus() == 300) {
					return response;
				}
		return service.save(form, principal);
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/update")
	public BaseResponse update(@RequestBody ReceptionReception_Registration_DTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
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
	@GetMapping("/getByDate/{date}")
	public BaseResponse getCurrent(@PathVariable(value = "date") String date,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getCurrent(date,principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getNextMLC")
	public BaseResponse getNextMLC(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getNextMLC(principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getNextTOKEN")
	public BaseResponse getNextTOKEN(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getNextTOKEN(principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getPatientDetailsByUHID/{uhid}")
	public BaseResponse getPatientDetailsByUHID(@PathVariable(value = "uhid") String uhid, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getPatientDetailsByUHID(uhid, principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/printReport/{format}/{id}/{type}")
	@ResponseBody
	public HttpEntity<byte[]> printReport(@PathVariable("format") String format, @PathVariable(value = "id") String id, @PathVariable(value = "type") String type) {
		final byte[] data = service.report_PDF(id,format,type);
		HttpHeaders header = new HttpHeaders();
		if(format.equals("pdf")){ header.setContentType(MediaType.APPLICATION_PDF); }
		if(format.equals("xlsx")) { header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); }
		String headerValue = "attachment; filename=Report_File("+new Timestamp(System.currentTimeMillis()) +")."+format;
		header.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
		if(data !=null) { header.setContentLength(data.length); }
		return new HttpEntity<byte[]>(data, header);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','USER')")
//	@GetMapping("/getUhidName")
//	public BaseResponse getUhidName(Principal principal, @RequestHeader("yearCd") Long yearCd) {
//		return service.getUhidName(principal);
//	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/filterUhidName/{searchtext}")
	public BaseResponse filterUhidName(@PathVariable(value = "searchtext") String searchtext,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.filterUhidName(searchtext,principal);
	}
	
  	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/searchOnTableData/{searchtext}")
	public BaseResponse searchOnTableData(@PathVariable(value = "searchtext") String searchtext,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.searchOnTableData(searchtext,principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getOnTableData/{searchtext}")
	public BaseResponse getOnTableData(@PathVariable(value = "searchtext") Long searchtext,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getOnTableData(searchtext,principal,yearCd);
	}
	
}
