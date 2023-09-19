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
@Table(name = "SubGroupMaster")
public class MasterOtherMasters1_SubGroup_Entity extends Auditable<String> {



	private String groupName;
	private String subGroup;
	private String sequenceNo;
	private String rate;
	private String companyId;

}
