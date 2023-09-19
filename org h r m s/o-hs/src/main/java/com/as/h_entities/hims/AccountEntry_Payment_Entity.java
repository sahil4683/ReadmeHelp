package com.as.h_entities.hims;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "AccountEntry_Payment")
public class AccountEntry_Payment_Entity extends Auditable<String> {



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
	private String userName;
	private String companyId;
	private String plasticInstrumentName;
	private String tdsyn; // yes no
	private String tdsAmount;
	private String porr; // refund payment

}
