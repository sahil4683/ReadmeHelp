package com.as.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactedDoctorResponseMap {

	private String uhid;
	private String billNo;
	private String ipdno;
	private String date;
	private String patientName;
	private String typeOfPatient;
	private String patientType;
	private String organization;
	private String groupName;
	private String particulars;
	private String total;
	private String netTotal;
	private String incentive;
	
}
