package com.as.h_dto.hims;

import lombok.Data;

@Data
public class RequestSheetIpd_Details_DTO {

	private Long id;

	private String requestId;
	private String sno;
	private String testName;
	private String testId;
	private String requestBy;
	private String labFlag;
	private String time;
	private String remarks;
	private String companyId;
	
}
