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
@Table(name = "Ipd_FinalIpdBill_Details")
public class IpdBill_FinalIpdBill_Details_Entity extends Auditable<String> {



	private String sno;
	private String ipdBillNo;
	private String testName;
	private String testCode;
	private String groupName;
	private String procedureDoctor1;
	private String procedureDoctor2;
	private String qty;
	private String rate;
	private String discount;
	private String discountPer;
	private String additional;
	private String less;
	private String newSubGroupCode;
	private String groupCode;
	private String drAdd;
	private String className;
	private String classCode;
	private String amount;
	private String orgcode;
	private String packageYn;
	private String packageName;
	private String pdate;
	private String ptime;
	private String packageFlag;
	private String serviceFlag;
	private String requestType;

}
