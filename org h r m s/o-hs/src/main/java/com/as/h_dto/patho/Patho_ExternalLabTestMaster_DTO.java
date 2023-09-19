package com.as.h_dto.patho;

import lombok.Data;

@Data
public class Patho_ExternalLabTestMaster_DTO {

	private Long id;
	
	private String groupName;
	private String testName;
	private String labName;
	private String organizationName;
	private String rate;
	
	private String incentiveRate;
	private String incentiveAmount;
	private String emergencyRate;
	private String emergencyAmount;
	
}
