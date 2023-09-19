package com.as.request;

import lombok.Data;

@Data
public class Report_Bill_Request {
	
	private String format;
	private String type;
	private String subtype;

	private String fromDate;
	private String toDate;
	private String creditAgainst;
	private String organizationName;
	private String userName;
	private String refBy;
	private String billType;
	private String packageName;
	private String insuranceCompany;
	private String subDept;
	private String typeOfPatient;
	private String consultant;
	
	//For Admission Discharge
	private String patientName;
	private String dischargeyn;
	
	//GroupWise
	private String groupTotal;
	//New
	private String groupName;
	private String testName;
	private String doctorName;
	
	//uhid
	private String ipdNo;
	private String receivedFrom;
	private String modeOfPayment;
	private String toAccount;
	private String part;
	private String department;
	private String instrumentName;
}
