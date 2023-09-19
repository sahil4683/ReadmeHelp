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
@Table(name = "TemplateMaster")
public class TemplateMaster_Entity extends Auditable<String> {



	private String templateType;
	private String template;
	private String companyId;

}
