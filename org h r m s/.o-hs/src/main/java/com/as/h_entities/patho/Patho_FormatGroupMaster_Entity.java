package com.as.h_entities.patho;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Patho_FormatGroupMaster")
public class Patho_FormatGroupMaster_Entity extends Auditable<String> {

	private String sno;
	private String testName;
	private String testCode;
	private String formatName;
	private String srno;
	private String medicineName;
	private String groupName;
	private String formatTest;
	private String formatTestId;
	@Lob
	private String note;
	private String avgTime;
	
}
