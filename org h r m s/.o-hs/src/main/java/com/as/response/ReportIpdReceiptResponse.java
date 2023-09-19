package com.as.response;

import lombok.Data;

@Data
public class ReportIpdReceiptResponse {

	
	private String uhid;
	private String name;
	private String organization;
	private String ipdNo;
	private String receiptNo;
	private String receivedFrom;
	private String dept;
	private String type;
	private String receipt;
	private String chequeDetails;
	private String narration;

	/*
	 * Constructor For IPD Receipt Register
	 */
	public ReportIpdReceiptResponse(String receiptNo, String uhid, String ipdNo, String receivedFrom, String name,
			String dept, String organization, String type, String receipt, String chequeDetails, String narration) {
		super();
		this.receiptNo = receiptNo;
		this.uhid = uhid;
		this.ipdNo = ipdNo;
		this.receivedFrom = receivedFrom;
		this.name = name;
		this.dept = dept;
		this.organization = organization;
		this.type = type;
		this.receipt = receipt;
		this.chequeDetails = chequeDetails;
		this.narration = narration;
	}

}
