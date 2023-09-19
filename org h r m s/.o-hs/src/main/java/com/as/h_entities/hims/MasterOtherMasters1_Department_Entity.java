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
@Table(name = "DepartmentMaster")
public class MasterOtherMasters1_Department_Entity extends Auditable<String> {

	private String dept;
	private String prefix;
	private String companyId;

}
