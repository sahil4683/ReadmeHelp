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
@Table(name = "Discharge")
public class Discharge_Entity extends Auditable<String> {



	private String uName;
	private String date;
	private String dtime;
	private String caseno;
	private String pname;
	private String ipdno;
	private String cpfno;
	private String bedNo;
	private String dtype;
	private String groupName;
	private String dfg;
	private String reception;
	private String shift;
	private String operator;
	private String companyId;

}
