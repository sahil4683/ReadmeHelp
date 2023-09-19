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
@Table(name = "GroupMaster")
public class MasterOtherMasters1_Group_Entity extends Auditable<String> {



	private String consultant;
	private String department;
	private String superGroup;
	private String groupName;
	private String counsultantInc;
	private String sequenceNo;
	private String printGroupName;
	private String companyId;
}
