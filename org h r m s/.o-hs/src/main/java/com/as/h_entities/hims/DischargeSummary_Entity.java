package com.as.h_entities.hims;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "DischargeSummary")
public class DischargeSummary_Entity extends Auditable<String> {

	@Column(length = 50)
	private String name;
	private String address;
	@Column(length = 10)
	private String uhid;
	@Column(length = 10)
	private String ipdno;
	private String age;
	@Column(length = 10)
	private String sex;
	@Column(length = 20)
	private String admissionDate;
	@Column(length = 20)
	private String admissionTime;
	@Column(length = 20)
	private String procedureDate;
	@Column(length = 20)
	private String procedureTime;
	@Column(length = 20)
	private String caseType;
	@Column(length = 20)
	private String dischargeType;
	@Column(length = 20)
	private String dischargeDate;
	@Column(length = 20)
	private String dischargeTime;

	private String refBy1;
	private String refBy2;
	private String refBy3;
	private String refBy4;

	private String consultant1;
	private String consultant2;
	private String consultant3;
	private String consultant4;

	private String condisc; // ****
	private String printTitle;

	@Column(length = 100)
	private String summaryTitle1;
	@Lob
	private String summary1;
	@Column(length = 100)
	private String summaryTitle2;
	@Lob
	private String summary2;
	@Column(length = 100)
	private String summaryTitle3;
	@Lob
	private String summary3;
	@Column(length = 100)
	private String summaryTitle4;
	@Lob
	private String summary4;
	@Column(length = 100)
	private String summaryTitle5;
	@Lob
	private String summary5;
	@Column(length = 100)
	private String summaryTitle6;
	@Lob
	private String summary6;
	@Column(length = 100)
	private String summaryTitle7;
	@Lob
	private String summary7;
	@Column(length = 100)
	private String summaryTitle8;
	@Lob
	private String summary8;
	@Column(length = 100)
	private String summaryTitle9;
	@Lob
	private String summary9;
	@Column(length = 100)
	private String summaryTitle10;
	@Lob
	private String summary10;
	@Column(length = 100)
	private String summaryTitle11;
	@Lob
	private String summary11;
	@Column(length = 100)
	private String summaryTitle12;
	@Lob
	private String summary12;
	@Column(length = 100)
	private String summaryTitle13;
	@Lob
	private String summary13;
	@Column(length = 100)
	private String summaryTitle14;
	@Lob
	private String summary14;
	@Column(length = 100)
	private String summaryTitle15;
	@Lob
	private String summary15;
	@Column(length = 100)
	private String summaryTitle16;
	@Lob
	private String summary16;
	@Column(length = 100)
	private String summaryTitle17;
	@Lob
	private String summary17;
	@Column(length = 100)
	private String summaryTitle18;
	@Lob
	private String summary18;
	@Column(length = 100)
	private String summaryTitle19;
	@Lob
	private String summary19;
	@Column(length = 100)
	private String summaryTitle20;
	@Lob
	private String summary20;
	@Column(length = 100)
	private String summaryTitle21;
	@Lob
	private String summary21;
	@Column(length = 100)
	private String summaryTitle22;
	@Lob
	private String summary22;
	@Column(length = 100)
	private String summaryTitle23;
	@Lob
	private String summary23;
	@Column(length = 100)
	private String summaryTitle24;
	@Lob
	private String summary24;
	@Column(length = 100)
	private String summaryTitle25;
	@Lob
	private String summary25;

	private String footer;
	@Column(length = 15)
	private String lastupdate;

//	private String companyId;

}
