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
@Table(name = "RequestSheetIpd")
public class RequestSheetIpd_Entity extends Auditable<String> {

	private String ipdno;
	private String uhid;
	private String date;
	private String organization;
	private String type;
	private String pathoFlag;
	private String remarks;
	private String companyId;
}
