package com.as.h_entities.hims;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CashDues")
public class ReceptionBill_CashDues_Entity extends Auditable<String> {



	private String receiptDate;
	private String receivedFrom;
	private String dept;
	private String uhid;
	private String receiptNo;
	private String billNo;
	private String amount;
	private String words;
	private String toAccount;
	private String against;
	private String type;
	private String chequeNo;
	private String plasticInstrumentName;
	private String transactionNo;
	private String dated;
	private String drawnOn;
	private String part;
	private String companyId;
	private String userName;
}
