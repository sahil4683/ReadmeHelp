package com.as.h_dto.hims;

import lombok.Data;

@Data
public class AccountEntry_Payment_DTO {

	private Long id;
	
	private String receiptDate;
	private String paidTo;
	private String paidFrom;
	private String amount;
	private String words;
	private String paymentNo;
	private String ipdno;
	private String uhid;
	private String against;
	private String type;
	private String chequeNo;
	private String dated;
	private String drawnOn;
	private String dept;
	private String UserName;
	private String companyId;
	private String plasticInstrumentName;
	private String tdsyn; //yes no
	private String tdsAmount;
	private String porr; //refund payment
	
}
