package com.as.h_dto.patho;

import lombok.Data;

@Data
public class Patho_AuditTrailPatho_DTO {
	
	private Long id;
	
	private String recordId;
	private String formName;
	private String testName;
	private String userName;
	private String type;
	private String recordDate;
	private String companyId;
	private String patientName;

	
}
