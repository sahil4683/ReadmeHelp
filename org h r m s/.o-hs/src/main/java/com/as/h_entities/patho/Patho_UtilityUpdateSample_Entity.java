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
@Table(name = "Patho_UtilityUpdateSample")
public class Patho_UtilityUpdateSample_Entity extends Auditable<String>  {
	
	private String uhid;
	private String barcode;

}
