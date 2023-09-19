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
@Table(name = "IpdDiagnosisMaster")
public class Ipd_DiagnosisSummery_Entity extends Auditable<String> {



	private String superGroup;
	private String groupName;
	private String subGroup;
	private String lcdCode;
	private String diagnosis;
	private String chapter;
	private String companyId;
}
