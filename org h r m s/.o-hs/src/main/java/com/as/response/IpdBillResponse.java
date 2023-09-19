package com.as.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IpdBillResponse{
	private String sno;
	private String description;
	private String amount;
	
	private String groupName;
	private String consultant;
	private String rate;
	private String qty;
	
	private String date;
	
	public IpdBillResponse(String sno, String description, String amount) {
		super();
		this.sno = sno;
		this.description = description;
		this.amount = amount;
	}

	public IpdBillResponse(String sno, String description, String amount, String groupName, String consultant, String rate,
			String qty, String date) {
		super();
		this.sno = sno;
		this.description = description;
		this.amount = amount;
		this.groupName = groupName;
		this.consultant = consultant;
		this.rate = rate;
		this.qty = qty;
		this.date = date;
	}

}