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
@Table(name = "DoctorReference")
public class DoctorReference_Entity extends Auditable<String> {



	private String name;
	private String degree;
	private String speciality;
	private String referringIncentive;
	private String expired;
	private String doj;
	private String address;
	private String city;
	private String dob;
	private String state;
	private String mcode;
	private String mobile;
	private String companyId;

	private String bloodGroup;
	private String officePhone;
	private String pan;
	private String pincode;
	private String residencePhone;
	private String fax;
	private String website;
	private String email;
	private String regNo;
}
