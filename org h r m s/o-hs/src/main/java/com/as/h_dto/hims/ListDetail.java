package com.as.h_dto.hims;

import lombok.Data;

@Data
public class ListDetail {

	private Long id;
	private String sno;
	private String date;
	private String time;
	private String testName;
	private String testCode;
	
	private String procedureDoctor1;
	private String procedureDoctor2;
	
	private String qty;
	private String rate;
	private String amount;
	
	private String billNo; //***

}
