package com.as.h_dto.hims;


import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReceptionBillOpdHealthCheckup_Details_DTO {

	private Long id;
	private String sno;
	@NotBlank
	private String groupName;
	@NotBlank
	private String particulars;
	private String procedureDoctor1;
	private String rate;
	private String qty;
	private String amount;

	private String billNo; //**
	private String testCode; //**
	
	private String subDept;
	private String subDeptId;
	
	private String packageYes; //Blank
	private String packageName;
	
}
