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
@Table(name = "Patho_FooterUpdation")
public class Patho_FooterUpdation_Entity extends Auditable<String> {

	private String footer1;
	private String footer2;
	private String haematologyMachineName;
	private String serologyMachineName;
	private String thyroidMachineName;
	private String biochemistryMachineName;
	private String electrolytesMachineName;
	private String coagulationMachineName;
	
}
