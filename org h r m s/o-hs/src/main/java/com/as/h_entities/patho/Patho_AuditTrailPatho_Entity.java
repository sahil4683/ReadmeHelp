package com.as.h_entities.patho;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.as.entities.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
@Table(name = "Patho_AuditTrailPatho")
@AllArgsConstructor
@NoArgsConstructor
public class Patho_AuditTrailPatho_Entity extends Auditable<String> {
	
	private String recordId;
	private String formName;
	private String testName;
	private String userName;
	private String type;
	private String recordDate;
	private String companyId;
	private String patientName;
	
}
