package com.as.h_dto.hims;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReceptionReception_Registration_DTO {
	
	private Long id;
	
	@NotBlank
	private String Name;	
	private String Photo;	
	@NotBlank
	private String Address;	
//	@NotBlank
	private String Area;	
	private String City;	
	private String Pin;	
	private String State;	
	private String Country;	
	private String UHID;	
	private String DOB;	
	private String Age;	
	@NotBlank
	private String Sex;	
	private String IDNoCaption;	
	private String IDNo;		
	private String Telephone;	
	private String MobileCode;	
	@NotBlank
	private String Mobile;	
	private String date;	
	private String Time;	
	private int ReferredBy;
	private int Consultant;	
	private int Organization;	
	private String PatientType;		
	private String UserName;	
	private String BARCODE;	
	private String TOKENNO;	
	private String Height;	
	private String Weight;	
	private String aadhar;	
	
	
	/*Unknown Columns
	 * */
	private String CASEN;
	private String Expired;
	private String ComapnyID; 
	private String ExporedYN;

	private String Email;	
	private String Religion;	
	private String Occupation;	
	private String Remark;	
	private String EditBy;	
	private String MariatalStatus;	
	private String WebId;	
	private String mlcno;	
	private String bno;	
	private String PatchUHID;	
	private String Patchid;	
	
	private String ConsultantName;	
	private String OrganizationName;
}
