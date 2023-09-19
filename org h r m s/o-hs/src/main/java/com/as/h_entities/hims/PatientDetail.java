package com.as.h_entities.hims;

import java.util.Date;

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
@Table(name = "PatientDetail")
public class PatientDetail extends Auditable<String> {


	private Date date;

	private String uHID;
	private String iPDNO;
	private String name;
	private String diagnosis;
	private String provisionalDiagnosis;
	private String advice;
	private Date nextVDate;
	private String serName;
	private String companyId;
	private int age;
	private String iInvastigation;
	private String temp;
	private String rr;
	private String spo2;
	private String bloodgrp;
	private int height;
	private String bp;
	private int weight;
	private int pulse;
	private String clinicalNote;
	private String remarks;

}