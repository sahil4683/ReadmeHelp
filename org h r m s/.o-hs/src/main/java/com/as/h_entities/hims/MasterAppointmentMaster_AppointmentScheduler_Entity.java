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
@Table(name = "AppointmentScheduler")
public class MasterAppointmentMaster_AppointmentScheduler_Entity extends Auditable<String> {



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
