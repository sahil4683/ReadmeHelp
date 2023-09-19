package com.as.response;

public interface ReportBillInterface {

	String getDate();
	String getBillNo();
	String getSubDept();
	String getUhid();
	String getName();
	String getRefBy();
	String getConsultant();
	String getOrganization();
	String getConcession();
	String getTrust();
	String getNetTotal();
	
	//Health Checkup
	String getTotal();
	String getPaid();
	String getDue();
	
	//IPD Register
	String getIpdNo();
	String getPackage();
	String getMedicineCharges();
	String getAdvance();
	String getDiscount();
	String getOtherDiscount();
	String getGrandTotal();

	
	
	//IPD Receipt
	String getReceiptNo();
	String getReceivedFrom();
	String getDept();
	String getType();
	String getReceipt();
	String getChequeDetails();
	String getNarration();
	
}
