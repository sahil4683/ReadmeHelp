package com.as.h_dto.hims;

import lombok.Data;

@Data
public class MasterOtherMasters1_DoctorIncentiveMaster_DTO {
	
	private Long id;
	
	private String doctorName;
//	private String department;
//	private String groupName;

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
