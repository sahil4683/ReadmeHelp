package com.as.controller.hims;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.as.request.Report_Bill_Request;
import com.as.service.hims.Reports_RoutineReports_Service;

@RestController
@RequestMapping("/report-routine_reports")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Reports_RoutineReports_Controller {

	@Autowired
	Reports_RoutineReports_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/printReport")
	@ResponseBody
	public HttpEntity<byte[]> printReport(@RequestBody Report_Bill_Request form,@RequestHeader("yearCd") Long yearCd) {
			final byte[] data = service.report_PDF(form,yearCd);
			HttpHeaders header = new HttpHeaders();
			if(form.getFormat().equals("pdf")){ header.setContentType(MediaType.APPLICATION_PDF); }
			if(form.getFormat().equals("xlsx")) { header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); }
			String headerValue = "attachment; filename=Report_File("+new Timestamp(System.currentTimeMillis()) +")."+form.getFormat();
			header.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
			if(data !=null) { header.setContentLength(data.length); }
			return new HttpEntity<byte[]>(data, header);
	}
	
}
