package com.as.response;

import lombok.Data;

@Data
public class AdmissionDischargeResponce {

	private String doa;
	private String admissionTime;
	private String uhid;
	private String name;
	private String ipdNo;
	private String dob;
	private String dischargeTime;
	private String age;
	private String bedNo;
	private String group;
	private String consultant;
	private String organization;
	private String insurenceCompany;
	private String discharge;
	
	public AdmissionDischargeResponce(String doa, String admissionTime, String uhid, String name, String ipdNo,
			String dob, String dischargeTime, String age, String bedNo, String group, String consultant,
			String organization, String insurenceCompany, String discharge) {
		super();
		this.doa = doa;
		this.admissionTime = admissionTime;
		this.uhid = uhid;
		this.name = name;
		this.ipdNo = ipdNo;
		this.dob = dob;
		this.dischargeTime = dischargeTime;
		this.age = age;
		this.bedNo = bedNo;
		this.group = group;
		this.consultant = consultant;
		this.organization = organization;
		this.insurenceCompany = insurenceCompany;
		this.discharge = discharge;
	}
	
	
}
