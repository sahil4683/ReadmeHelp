package com.as.h_dto.hims;

import lombok.Data;

@Data
public class IpdDischange_DischargeClearance_DTO {

	private Long id;
	
	private String userName;
	private String date;
	private String dTime;
	private String uhid; //( CASENO )
	private String name;
	private String ipdno;
	private String cpfno;
	private String bedNo;
	private String dischargeType; //( DISCHARGE / EXPIRED / TRANSFER )
	private String group;
	private String dfg;
	private String reception;
	private String shift;
	private String operator;
	private String companyId;
}
