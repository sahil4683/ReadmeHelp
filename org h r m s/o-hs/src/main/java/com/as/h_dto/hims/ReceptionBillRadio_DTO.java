package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReceptionBillRadio_DTO {
	
	private Long id;
	private String billType;
	private String date;
	private String subDept;
	private String patientTypeOldNew;
	@NotBlank
	private String billNo;
	private String ipdno;
	@NotBlank
	private String uhid;
	private String organization;// *
	private String ptype;
	private String refBy1;
	private String refBy2;
	@NotBlank
	private String consultant1;
	private String consultant2;
	private String methodOfPayment;
	private String chequeNo; //##
	private String plasticInstrumentName;
	private String transactionNo; //##
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

	private String time; //**
	private String userName; //**
	private String companyId; //**
	
	private String creditAgainst;
	
	private String trustPer; 
	private String trustAmount; 
	private String trustDiscount; 
	
	List<ReceptionBillRadio_Details_DTO> detailsList=new ArrayList<>();
	
	@Transient
	private String name;
	
//	private String TrustPer;
//	private String TrustNOamt;
//	private String TrustDiscount;
//
//	private String CreditAgainst;
//	private String LocFlag;
//	private String LocID;
//	private String RadioFlag;
//	private String EstimateBillNo
}
