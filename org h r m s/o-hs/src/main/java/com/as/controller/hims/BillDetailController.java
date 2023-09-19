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

import com.as.h_dto.hims.BillDetailDTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.BillDetailService;
import com.as.utils.ApiValidationUtility;

@RestController
@RequestMapping("/billDetail")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BillDetailController {
	
	@Autowired
	BillDetailService billDetailService;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/findAll")
	public BaseResponse findAll(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return billDetailService.findAll(principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getByDate/{date}")
	public BaseResponse getCurrent(@PathVariable(value = "date") String date,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return billDetailService.getCurrent(date,principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getDetailsById/{id}")
	public BaseResponse getDetailsById(@PathVariable(value = "id") String id, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response = new BaseResponse();
		if (id == null) {
			response.setStatus(300);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Please review data submitted");
			return response;
		}
		return billDetailService.getDetailsById(id, principal,yearCd);
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public BaseResponse save(@RequestBody BillDetailDTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response =  new ApiValidationUtility().validateAPI(form);
		if(response.getStatus() == 300) {
			return response;
		}
		return billDetailService.save(principal, form);
	}

	@PostMapping("/update")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public BaseResponse update(@RequestBody BillDetailDTO form, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response =  new ApiValidationUtility().validateAPI(form);
		if(response.getStatus() == 300) {
			return response;
		}
		return billDetailService.update(principal, form);
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
		return billDetailService.delete(id, principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/deleteDetailById/{id}")
	public BaseResponse deleteDetailById(@PathVariable(value = "id") long id, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		BaseResponse response = new BaseResponse();
		if (id == 0) {
			response.setStatus(300);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Please review data submitted");
			return response;
		}
		return billDetailService.deleteDetailById(id, principal);
	}
	

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getLabRequest/{ipdno}")
	public BaseResponse getLabRequest(@PathVariable(value = "ipdno") String ipdno, Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return billDetailService.getLabRequest(ipdno, principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/searchOnTableData/{searchtext}")
	public BaseResponse searchOnTableData(@PathVariable(value = "searchtext") String searchtext,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return billDetailService.searchOnTableData(searchtext,principal,yearCd);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getOnTableData/{searchtext}")
	public BaseResponse getOnTableData(@PathVariable(value = "searchtext") Long searchtext,Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return billDetailService.getOnTableData(searchtext,principal,yearCd);
	}
	
}
