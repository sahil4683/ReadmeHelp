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
@Table(name = "ExternalRadioMaster")
public class MasterOtherMasters2_ExternalRadioMaster_Entity extends Auditable<String> {



	private String radioName;
	private String address;
	private String contactPerson;
	private String phoneNo;
	private String email;
	private String companyId;

}
