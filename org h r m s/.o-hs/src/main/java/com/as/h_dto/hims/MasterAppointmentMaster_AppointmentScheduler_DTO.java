package com.as.h_dto.hims;

import lombok.Data;

@Data
public class MasterAppointmentMaster_AppointmentScheduler_DTO {
	
	private Long id;

	private String doctorName;
	private String slotTime;
	private String startTime;
	private String endTime;
	
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	
	private String companyId;
	
}
