package com.as.controller.hims;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.as.service.hims.Reports_CashDue_Service;

@RestController
@RequestMapping("/report-register_report-cash_due_report")
@CrossOrigin(origins = "*", maxAge = 3600)
public class Reports_CashDue_Controller {

	@Autowired
	Reports_CashDue_Service service;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/printReport/{format}")
	@ResponseBody
	public HttpEntity<byte[]> printReport(@PathVariable("format") String format, @RequestHeader("yearCd") Long yearCd) {
			final byte[] data = service.report_PDF(format,yearCd);
			HttpHeaders header = new HttpHeaders();
			if(format.equals("pdf")){ header.setContentType(MediaType.APPLICATION_PDF); }
			if(format.equals("xlsx")) { header.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); }
			String headerValue = "attachment; filename=Report_File("+new Timestamp(System.currentTimeMillis()) +")."+format;
			header.set(HttpHeaders.CONTENT_DISPOSITION, headerValue);
			if(data !=null) { header.setContentLength(data.length); }
			return new HttpEntity<byte[]>(data, header);
	}
	
}
