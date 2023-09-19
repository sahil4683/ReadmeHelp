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
@Table(name = "MasterCategory")
public class Master_Category_Entity extends Auditable<String> {



	private String categoryName;
	private String categoryType;
	private String companyId;
}
