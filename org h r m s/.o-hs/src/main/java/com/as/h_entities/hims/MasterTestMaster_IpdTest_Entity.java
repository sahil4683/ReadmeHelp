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
@Table(name = "IpdTestMaster")
public class MasterTestMaster_IpdTest_Entity extends Auditable<String> {



	private String superGroup;
	private String groupName;
	private String subDepartment;
	private String grade;
	private String testName;
	private String testNamePrint;
	private String organizationName;
	private String dailyYn;
	private String dailyQty;
	private String costRate;
	private String automaticOnFinalIPDBill;

	private String general;
	private String nicu;
	private String semiSpecial;
	private String special;
	private String deluxe;
	private String executive;
	private String icu;
	private String emergency;

	private String companyId;

	private String ordinal;
	private String automatic;
	private String formatDesign;
	private String incPer;
	private String incAmt;
	private String externalYn;
	private String externalLabName;

}
