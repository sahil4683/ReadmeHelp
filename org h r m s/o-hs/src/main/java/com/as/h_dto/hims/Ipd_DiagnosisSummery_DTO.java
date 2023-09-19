package com.as.h_dto.hims;

import lombok.Data;

@Data
public class Ipd_DiagnosisSummery_DTO {

	private Long id;

	private String superGroup;
	private String groupName;
	private String subGroup;
	private String lcdCode;
	private String diagnosis;
	private String chapter;
	private String companyId;

	
}
