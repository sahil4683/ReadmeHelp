package com.as.h_dto.patho;

import lombok.Data;

@Data
public class Patho_ObservationMaster_DTO {

	private Long id;	
	private String testName;	
	private String formatName;	
	private String mGroup;	
	private String method;	
	private String defaultValue;
	private String specimen;	
	private String multiline;
	private String formula;
	
	private String paediatric;
	private String isLable;
		
	private String critical;	
	private String critical1;	
	private String critical2;	
	private String critical3;

	private String mfrom1;	
	private String mfunit1;		
	private String mto1;	
	private String mtunit1;
	private String mfrom2;	
	private String mfunit2;	
	private String mto2;
	private String mtunit2;

	private String ffrom1;	
	private String ffunit1;		
	private String fto1;	
	private String ftunit1;
	private String ffrom2;	
	private String ffunit2;		
	private String fto2;	
	private String ftunit2;
	
}
