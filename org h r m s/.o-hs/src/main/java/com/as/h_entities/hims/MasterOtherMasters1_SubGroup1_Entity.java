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
@Table(name = "SubGroupMaster1")
public class MasterOtherMasters1_SubGroup1_Entity extends Auditable<String> {



	private String subGroup;
	private String subGroup1;
	private String companyId;

}
