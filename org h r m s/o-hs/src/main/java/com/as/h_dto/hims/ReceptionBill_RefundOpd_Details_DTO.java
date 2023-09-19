package com.as.h_dto.hims;

import lombok.Data;

@Data
public class ReceptionBill_RefundOpd_Details_DTO {

	private Long id;
	private String sno;
	private String groupName;
	private String particulars;
	private String procedureDoctor1;
	private String procedureDoctor2;
	private String rate;
	private String qty;
	private String amount;

	private String billNo; //**
	private String testCode; //**
	
}