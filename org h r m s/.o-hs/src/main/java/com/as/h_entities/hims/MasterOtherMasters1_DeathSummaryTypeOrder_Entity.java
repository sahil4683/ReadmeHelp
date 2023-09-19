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
@Table(name = "DeathSummaryTypeOrder")
public class MasterOtherMasters1_DeathSummaryTypeOrder_Entity extends Auditable<String> {



	private String typeId;
	private String footer;

	private String companyId;

}
