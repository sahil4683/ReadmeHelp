package com.as.response;

import lombok.Data;

@Data
public class CashDueResponse {

	 private String patientName;
	 private String name;
     private String uhid;
     private String billNo;
     private String dept;
     private String subDept;
     private String nettotal;
     private String paidAmount;
     private String total;
     private String due;
     private String date;
     
     private String paid;
     
	public CashDueResponse(String patientName, String uhid, String billNo, String dept, String nettotal,
			String paidAmount, String due, String date, String total) {
		super();
		this.patientName = patientName;
		this.name = this.patientName;
		this.uhid = uhid;
		this.billNo = billNo;
		this.dept = dept;
		this.subDept = this.dept;
		this.nettotal = nettotal;
		this.paidAmount = paidAmount;
		this.paid = this.paidAmount;
		this.due = due;
		this.date = date;
		this.total = total;
	}
	
	
	public CashDueResponse(String patientName, String uhid, String billNo, String dept, String date, String due, String paid, String total) {
		super();
		this.name = patientName;
		this.uhid = uhid;
		this.billNo = billNo;
		this.dept = dept;
		this.subDept = dept;
		this.paid = paid;
		this.due = due;
		this.date = date;
		this.total = total;
	}

	
}
