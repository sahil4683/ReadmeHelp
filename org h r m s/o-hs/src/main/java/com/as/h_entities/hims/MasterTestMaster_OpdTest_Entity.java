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
@Table(name = "OpdTestMaster")
public class MasterTestMaster_OpdTest_Entity extends Auditable<String> {



	private String superGroup;
	private String groupName;
	private String subGroup;
	private String subDepartment;
	private String testName;
	private String organizationName;
	private String rate;
	private String costRate;
	private String description;
	private String companyId;

	private String activeYn;
	private String oldTestName;
	private String formatdesign;
	private String incPer;
	private String incAmt;

}
