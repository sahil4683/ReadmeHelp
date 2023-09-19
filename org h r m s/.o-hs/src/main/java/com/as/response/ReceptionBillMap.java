package com.as.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceptionBillMap {
	private String sno;
	private String particulars;
	private String procedureDoctor1;
	private String rate;
	private String qty;
	private String amount;
}
