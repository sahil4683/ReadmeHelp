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
@Table(name = "shifting")
public class Shifting extends Auditable<String> {



	private String uhid;
	private String ipdno;

	private String admitDate;

	private String pname;

	private String PreBedNo;
	private String shiftBedNo;

	private String shiftDate;
	private String shiftTime;

	private String shiftGroup;
	private String preGroup;

	private String companyID;
}
