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
@Table(name = "ReceptionBill_RefundRadio_Details")
public class ReceptionBill_RefundRadio_Details_Entity extends Auditable<String> {



	private String sno;
	private String groupName;
	private String particulars;
	private String procedureDoctor1;
	private String procedureDoctor2;
	private String rate;
	private String qty;
	private String amount;
//Manually Set
	private String billNo;
	private String testCode;
}
