package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MasterOtherMasters1_RefDoctorIncentiveMaster_DTO {

	private Long id;
	
	private String doctorName;
	private String department;
	private String doctorAmount;
	private String doctorInc;
	private String companyId;
	
	List<MasterOtherMasters1_RefDoctorIncentiveMaster_Details_DTO> details = new ArrayList<>();
}
