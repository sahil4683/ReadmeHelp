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
@Table(name = "ConcessionMaster")
public class MasterOtherMasters1_Concession_Entity extends Auditable<String> {



	private String concessionName;
	private String concession;
	private String authName;
	private String companyId;

}
