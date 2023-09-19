package com.as.h_dto.hims;

import lombok.Data;

@Data
public class MasterTestMaster_OpdTest_DTO {

	private Long id;

	private String superGroup;
	private String groupName;
	private String subGroup;
	private String subDepartment;
	private String testName;
	private String organizationName;
	private String rate;
	private String costRate;
	private String description;
	private String companyId;
	
	private String activeYn;
	private String oldTestName;
	private String formatdesign;
	private String incPer;
	private String incAmt;
	
}
