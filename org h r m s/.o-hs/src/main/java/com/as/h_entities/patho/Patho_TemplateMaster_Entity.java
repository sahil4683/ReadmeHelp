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
@Table(name = "Patho_TemplateMaster")
public class Patho_TemplateMaster_Entity extends Auditable<String>{

	private String sno;
	private String formatName;
	private String srno;
	private String medicineName;
	private String formatTest;
	@Lob
	private String details;
	
}
