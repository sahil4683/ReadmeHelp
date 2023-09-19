package com.as.h_dto.hims;

import lombok.Data;

@Data
public class MasterPackageMaster_OpdPackageMaster_Details_DTO {
	
	private Long id;

	private String packageId;
	private String sno;
	private String testName;
	private String testCode;
	private String rate;
	private String qty;
	private String amount;
	private String groupName;
	private String newSubGroupCode;
	private String groupCode;
	
	private String procedureDoctor;
}
