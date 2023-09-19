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
@Table(name = "OrganizationMaster")
public class OrganizationMaster_Entity extends Auditable<String> {



	private String organization;
	private String contactPerson;
	private String designation;
	private String address;
	private String city;
	private String state;
	private String countryCode;
	private String mobile;
	private String paid;
	private String companyId;

	private String email;
	private String website;
	private String telephone;

}
