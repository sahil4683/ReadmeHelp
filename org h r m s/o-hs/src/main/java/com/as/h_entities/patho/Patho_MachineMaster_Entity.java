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
@Table(name = "Patho_MachineMaster")
public class Patho_MachineMaster_Entity extends Auditable<String> {

	private String machineName;
	private String remarks;
	
}
