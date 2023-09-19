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
@Table(name = "RequestSheetIpd_Details")
public class RequestSheetIpd_Details_Entity extends Auditable<String> {



	private String requestId;
	private String sno;
	private String testName;
	private String testId;
	private String requestBy;
	private String labFlag;
	private String time;
	private String remarks;
	private String companyId;
}
