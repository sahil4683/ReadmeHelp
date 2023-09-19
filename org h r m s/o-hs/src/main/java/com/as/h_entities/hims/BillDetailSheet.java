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
@Table(name = "BillDetailSheet")
public class BillDetailSheet extends Auditable<String> {



	private String uhid;
	private String ipdno;
	private String date;
	private String highRisk;
	private String emergency;
	private String organization;
	private String total;
	private String advance;
	private String netTotal;
	private String companyId;

	private String dailyTest; // ArrayList To String

}
