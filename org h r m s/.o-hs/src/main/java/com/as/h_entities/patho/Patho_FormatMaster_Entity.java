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
@Table(name = "Patho_FormatMaster")
public class Patho_FormatMaster_Entity extends Auditable<String> {

	private String formatName;
	private String sampleName;
	private String color;
	private String machineName;
	
}
