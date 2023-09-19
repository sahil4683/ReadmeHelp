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
@Table(name = "TdsMaster")
public class MasterOtherMasters1_TDSMaster_Entity extends Auditable<String> {



	private String category;
	private String status;
	private String yearlyThreshold;
	private String cumulative;
	private String tdsRate;
	private String companyId;

}
