package com.as.h_entities.hims;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "AccountEntry_Receipt")
public class AccountEntry_Receipt_Entity extends Auditable<String> {



	private String receiptDate;
	private String receivedFrom;
	private String amount;
	private String words;
	private String toAccount;
	private String ipdno;
	private String uhid;
	private String against;
	private String type;
	private String part;
	private String chequeNo;
	private String dated;
	private String drawnOn;
	private String dept;
	private String userName;
	@Lob
	private String compose;
	private String companyId;
	private String plasticInstrumentName;
	private String name;
	private String receiptNo;

	private String BILLNO;
	private String BRFLG;
	private String panno;

}
