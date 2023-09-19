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
@Table(name = "WardMaster")
public class MasterOtherMasters2_WardMaster_Entity extends Auditable<String> {



	private String className;
	private Long classid;
	private String wardName;
	private String remarks;
	private String companyId;

}
