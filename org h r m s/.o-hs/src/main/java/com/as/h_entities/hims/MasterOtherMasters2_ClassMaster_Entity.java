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
@Table(name = "ClassMaster")
public class MasterOtherMasters2_ClassMaster_Entity extends Auditable<String> {



	private String className;
	private String ordinal;
	private String ordinalsequence;
	private String companyId;

}
