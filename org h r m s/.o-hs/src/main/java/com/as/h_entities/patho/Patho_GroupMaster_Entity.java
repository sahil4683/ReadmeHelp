package com.as.h_entities.patho;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Patho_GroupMaster")
public class Patho_GroupMaster_Entity extends Auditable<String> {

	private String groupName;
	
}
