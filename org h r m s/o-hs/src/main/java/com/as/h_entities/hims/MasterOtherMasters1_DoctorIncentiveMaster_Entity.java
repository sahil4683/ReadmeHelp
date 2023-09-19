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
@Table(name = "DoctorIncentiveMaster")
public class MasterOtherMasters1_DoctorIncentiveMaster_Entity extends Auditable<String> {


	private String doctorName;
//	private String department;
//	private String groupName;
	
	private String doctorNameView;
	private String departmentView;
	private String groupNameView;

	private String stayDoctor;
	private String stayHospital;

	private String visitDoctor;
	private String visitHospital;

	private String visitRefferedDoctor;
	private String visitRefferedHospital;

	private String scDoctor;
	private String scHospital;

	private String consultationDoctor;
	private String consultationHospital;

	private String ecgDoctor;
	private String ecgHospital;

	private String echoDoctor;
	private String echoHospital;

	private String otherDoctor;
	private String otherHospital;

}
