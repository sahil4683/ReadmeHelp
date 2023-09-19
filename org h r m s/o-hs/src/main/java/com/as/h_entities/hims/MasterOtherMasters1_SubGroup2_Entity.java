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
@Table(name = "SubGroupMaster2")
public class MasterOtherMasters1_SubGroup2_Entity extends Auditable<String> {



	private String subGroup1;
	private String subGroup2;
	private String companyId;

}
