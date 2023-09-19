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
@Table(name = "SuperGroupMaster")
public class MasterOtherMasters1_SuperGroup_Entity extends Auditable<String> {



	private String superGroup;
	private String accountName;
	private String companyId;
}