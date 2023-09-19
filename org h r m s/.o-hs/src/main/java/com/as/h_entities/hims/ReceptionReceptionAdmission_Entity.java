package com.as.h_entities.hims;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.as.entities.Auditable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "reception_reception_admission")
public class ReceptionReceptionAdmission_Entity extends Auditable<String> {

	private String date;
	private String Time;
	private String UHID;
	private String IPDNO;
	@Transient
	private String name;
	private String BEDNO;
	private String CPFNoLbl;
	private String CPFNO;
	private String GROUP_S;
	private String age;
	private String Sex;
	private int RefBy1;
	private int RefBy2;
	private int Consultant1;
	private int Consultant2;
	private int Organization;
	private String ADMITTEDDept;
	private String PatientType;
	private String Witness1;
	private String Witness2;
	private String Contact1;
	private String Contact2;
	private String typeofPayment;
	private String typeofPatient;
	private String GetPassDate;
	private String InsuranceCom;
	private String INC_Ex;
	private String CompanyId;
	private String UserName;
	private String discharge;
	private String tpaname;
	private String claimno;
	private String policyno;
	private String LocFlag;
	private String LocID;
	private String wardname;
	private String disflag;

}
