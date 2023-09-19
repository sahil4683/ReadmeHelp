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
@Table(name = "ExternalLabMaster")
public class MasterOtherMasters2_ExternalLabMaster_Entity extends Auditable<String> {



	private String labName;
	private String address;
	private String contactPerson;
	private String phoneNo;
	private String email;
	private String companyId;

}
