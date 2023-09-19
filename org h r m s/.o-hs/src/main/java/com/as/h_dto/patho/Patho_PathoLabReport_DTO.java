package com.as.h_dto.patho;

import lombok.Data;

@Data
public class Patho_PathoLabReport_DTO {
	
	private Long id;
	
	private String srno;
	private String date;
	
	private String patientId;
	private String patientName;
	private String uhid;
	private String ipdno;
	private String dob;
	private String gender;
	private String age;
	private String mobile;
	
	private String patientType;
	private String referenceDoctor;
	private String deptId;
	private String dept;
	private String billNo;
	
	private String testId;
	private String testName;
	
	private String timeOfCollection;
	private String timeOFReceived;
	private String sampleReceived;
	
	private String specimen;
	private String flag;
	private String status;
	private String labName;
	
	private String entredBy;
	private String entredByDesignation;
	private String verifyBy;
	private String verifyByDesignation;
	
	private boolean waitingFlag;
	private boolean collectedFlag;
	private boolean receivedFlag;
	private boolean printedFlag;
	
	private String companyId;
	private String subLocationId;
	private String activityId;
	
	private String reportTime;
	private String reviseTime;
	private String reviseDate;
	private String imp_eflag;
}
