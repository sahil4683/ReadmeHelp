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
@Table(name = "DischargeSummaryTemplateMaster")
public class MasterOtherMasters1_DischargeSummaryTemplateMaster_Entity extends Auditable<String> {



	private String templateType;
	private String templateName;
	private String template;
	private String companyId;

}
