package com.as.h_dto.hims;

import lombok.Data;

@Data
public class Shifting_DTO {
	
	private Long id;
	
	private String uhid;
	private String ipdno;
	
	private String admitDate;
	
	private String pname;
	
	private String PreBedNo;
	private String shiftBedNo;
	
	private String shiftDate; 
	private String shiftTime; 

	private String shiftGroup;
	private String preGroup;
	
	private String companyID;
	
}
