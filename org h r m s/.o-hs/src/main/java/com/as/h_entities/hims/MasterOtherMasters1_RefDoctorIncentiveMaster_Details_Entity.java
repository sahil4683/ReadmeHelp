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
@Table(name = "RefDoctorIncentiveMaster_Details")
public class MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity extends Auditable<String> {



	private String incMasterId;
	private String groupName;

}
