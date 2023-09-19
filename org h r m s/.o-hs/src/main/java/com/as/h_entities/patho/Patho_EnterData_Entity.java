package com.as.h_entities.patho;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.as.entities.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Patho_Patho_EnterData")
@AllArgsConstructor
@NoArgsConstructor
public class Patho_EnterData_Entity extends Auditable<String> {

	private String date;	
	private String uhid;	
	private String ipdno;
	private String sno;
	private String testId;
	private String testName;
	private String formateName;
	private String groupName;
	private String ftestId;	
	private String unit;
	private String normalRange;
	private String obvValue;
	private String status;
	private String low;
	private String high;
	private String hl;
	private String sequence;
	private String lable;
	private String companyId;
	private String subLocationId;
	private String activityId;
	private boolean recheckSample;
	private String machineName;
	private String flagMachine;
	private String barcode;
	private String formattest;
	private String note;
	private String statusName;
	private String userName;
	private String userNamePrint;
	private String sampleDevice;
	private String varifyBy;
	private String varifyTime;
	private String enterTime;
	private String varifyDate;	
	private String enterDate;	
	private String critical;	
	@Transient
	private boolean obvValueInvalid;
}
