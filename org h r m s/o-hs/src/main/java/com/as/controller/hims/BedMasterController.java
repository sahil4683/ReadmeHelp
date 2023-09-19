package com.as.controller.hims;

import java.io.ByteArrayInputStream;
import java.security.Principal;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.service.hims.BedMasterService;

@RestController
@RequestMapping("/bedmaster")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BedMasterController {
	
	private static final Logger logger = Logger.getLogger(BedMasterController.class.getName());
	
	@Autowired
	BedMasterService service; 

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getBedList")
	public BaseResponse getBedList(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		logger.info("Enter into BedMasterController getBedList()");

		return service.getBedList(principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/getBedStatus")
	public BaseResponse getAllBedList(Principal principal, @RequestHeader("yearCd") Long yearCd) {
		return service.getAllBedList(principal);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@RequestMapping(value = "/vacantbedreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> vacentBedReport() { 

		logger.info("Enter into BedMasterController vacentBedReport()");
		
        ByteArrayInputStream bis = service.vacentBedReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=vacantbeds.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
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
