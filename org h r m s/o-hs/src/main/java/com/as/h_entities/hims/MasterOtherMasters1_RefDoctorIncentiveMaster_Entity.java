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
@Table(name = "RefDoctorIncentiveMaster")
public class MasterOtherMasters1_RefDoctorIncentiveMaster_Entity extends Auditable<String> {



	private String doctorName;
	private String department;
	private String doctorAmount;
	private String doctorInc;
	private String companyId;

}
