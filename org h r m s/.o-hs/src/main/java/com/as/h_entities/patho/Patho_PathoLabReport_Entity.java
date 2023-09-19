package com.as.h_entities.patho;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Patho_Patient_Master")
public class Patho_PathoLabReport_Entity extends Auditable<String> {
	
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
	private String ReferredBy;
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
