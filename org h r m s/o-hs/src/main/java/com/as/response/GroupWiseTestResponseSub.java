package com.as.response;

import lombok.Data;

@Data
public class GroupWiseTestResponseSub {

	private String uhid;
	private String date;
	private String billNo;
	private String operator;
	private String name;
	private String testName;
	private Integer rate;
	private String doctorName;
	
	public GroupWiseTestResponseSub(String uhid, String date, String billNo, String operator, String name, String testName,
			Integer rate, String doctorName) {
		super();
		this.uhid = uhid;
		this.date = date;
		this.billNo = billNo;
		this.operator = operator;
		this.name = name;
		this.testName = testName;
		this.rate = rate;
		this.doctorName = doctorName;
	}
	
	
}
