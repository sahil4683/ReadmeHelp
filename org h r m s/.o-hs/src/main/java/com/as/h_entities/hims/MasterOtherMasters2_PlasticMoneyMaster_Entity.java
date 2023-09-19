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
@Table(name = "PlasticMoneyMaster")
public class MasterOtherMasters2_PlasticMoneyMaster_Entity extends Auditable<String> {



	private String plasticInstrumentName;
	private String bankName;
	private String companyId;

}
