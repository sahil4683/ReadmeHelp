package com.as.h_entities.patho;

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
@Table(name = "Patho_ExternalLabTestMaster")
public class Patho_ExternalLabTestMaster_Entity extends Auditable<String>{

	private String groupName;
	private String testName;
	private String labName;
	private String organizationName;
	private String rate;
	
	private String incentiveRate;
	private String incentiveAmount;
	private String emergencyRate;
	private String emergencyAmount;
	
}
