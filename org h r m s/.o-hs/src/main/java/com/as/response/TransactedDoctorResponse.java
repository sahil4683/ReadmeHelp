package com.as.response;

public interface TransactedDoctorResponse {

	String getDoctorId();
	String getDoctorName();
	
	/* FOR Report */
	String getUhid();
	String getBillNo();
	String getIpdno();
	String getDate();
	String getPatientName();
	String getTypeOfPatient();
	String getPatientType();
	String getOrganization();
	String getGroupName();
	String getParticulars();
	Integer getTotal();
	Integer getNetTotal();
	Integer getIncentive();
	
}
