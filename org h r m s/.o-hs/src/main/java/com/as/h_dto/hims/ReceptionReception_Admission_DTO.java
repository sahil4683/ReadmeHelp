package com.as.h_dto.hims;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReceptionReception_Admission_DTO {
	
	private Long id;
	
	private String date;	
	private String Time;
	@NotBlank
	private String UHID;
	@NotBlank
	private String IPDNO;	
	private String name;
	@NotBlank
	private String BEDNO;	
	private String CPFNoLbl;	
	private String CPFNo;	
	private String GROUP;	
	private String age;	
	private String Sex;	
	private int RefBy1;	
	private int RefBy2;	
	private int Consultant1;	
	private int Consultant2;	
	private int Organization;	
	private String ADMITTEDDept;	
	private String PatientType;	
	@NotBlank
	private String Witness1;	
	private String Witness2;	
	private String Contact1;	
	private String Contact2;	
	private String typeofPayment;	
	private String typeofPatient;	
	private String GetPassDate;	
	private String InsuranceCom;	
	private String INC_Ex;	
	private String CompanyId;	
	private String UserName;	
	private String DISCHARGE;	
	private String tpaname;	
	private String claimno;	
	private String policyno;	
	private String LocFlag;	
	private String LocID;	
	private String wardname;	
	private String disflag;
			
}
