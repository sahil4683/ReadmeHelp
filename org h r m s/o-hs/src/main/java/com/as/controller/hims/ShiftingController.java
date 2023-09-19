package com.as.controller.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.as.h_dto.hims.Shifting_DTO;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.ShiftingService;

@RestController
@RequestMapping("/shifting")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShiftingController {

	@Autowired
	private ShiftingService shiftingService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<BaseResponse> saveShifting(@RequestBody Shifting_DTO shiftingRequest,@RequestHeader("yearCd") Long yearCd) {
		BaseResponse response = shiftingService.save(shiftingRequest,yearCd);
		return ResponseEntity.ok(response);

	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/history")
	public ResponseEntity<BaseResponse> getShiftingHistory(@RequestHeader("yearCd") Long yearCd) {
		BaseResponse response = shiftingService.getShiftingHistory(yearCd);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER') || hasRole('USER')")
	@GetMapping("/getByDate/{date}")
	public BaseResponse getCurrent(@PathVariable(value = "date") String date, @RequestHeader("yearCd") Long yearCd,Principal principal) {
		return shiftingService.getCurrent(date, yearCd,principal);
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
		return shiftingService.delete(id, principal);
	}

}
