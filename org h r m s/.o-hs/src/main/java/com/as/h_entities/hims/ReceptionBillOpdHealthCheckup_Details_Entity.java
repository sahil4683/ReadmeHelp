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
@Table(name = "ReceptionBillOpdHealthCheckup_Details")
public class ReceptionBillOpdHealthCheckup_Details_Entity extends Auditable<String> {



	private String sno;
	private String groupName;
	private String particulars;
	private String procedureDoctor1;
	private String rate;
	private String qty;
	private String amount;

	private String billNo; // **
	private String testCode; // **

	private String subDept;
	private String subDeptId;

	private String packageYes; // Blank
	private String packageName;

}
