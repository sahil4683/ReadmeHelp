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
@Table(name = "ReceptionBill_RefundLab")
public class ReceptionBill_RefundLab_Entity extends Auditable<String> {



	private String billType;
	private String date;
	private String subDept;
	private String patientTypeOldNew;
	private String billNo;
	private String ipdno;
	private String uhid;
	private String organization;// *
	private String ptype;
	private String refBy1;
	private String refBy2;
	private String consultant1;
	private String consultant2;
	private String methodOfPayment;
	private String chequeNo; // ##
	private String plasticInstrumentName;
	private String transactionNo; // ##
	private String dated;
	private String drawnOn;
	private String remarks;
	private String details;
	private String total;
	private String concessionType;
	private String concessionPer;
	private String concession;
	private String nettotal;
	private String paidAmount;
	private String due;

	private String time; // **
	private String userName; // **
	private String companyId; // **

	private String creditAgainst;

	private String trustPer;
	private String trustAmount;
	private String trustDiscount;

	private String labBillNo;

}
