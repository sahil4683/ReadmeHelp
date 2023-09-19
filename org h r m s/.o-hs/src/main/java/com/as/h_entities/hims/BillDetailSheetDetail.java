package com.as.h_entities.hims;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "BillDetailSheetDetail")
public class BillDetailSheetDetail extends Auditable<String> {


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

	// Manually Set
	private String billNo; // ***
}
