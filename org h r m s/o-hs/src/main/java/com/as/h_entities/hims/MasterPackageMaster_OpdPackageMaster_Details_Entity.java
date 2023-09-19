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
@Table(name = "OpdPackageMaster_Details")
public class MasterPackageMaster_OpdPackageMaster_Details_Entity extends Auditable<String> {


	private String packageId;
	private String sno;
	private String testName;
	private String testCode;
	private String rate;
	private String qty;
	private String amount;
	private String groupName;
	private String newSubGroupCode;
	private String groupCode;

	private String procedureDoctor;
}
