package com.as.h_dto.hims;

import lombok.Data;

@Data
public class BedStatus_DTO {
	private String bedNumber;
	private String consultantName;
	private String patientName;
	private boolean isOccupied;	
	
	private String patientType;
}
