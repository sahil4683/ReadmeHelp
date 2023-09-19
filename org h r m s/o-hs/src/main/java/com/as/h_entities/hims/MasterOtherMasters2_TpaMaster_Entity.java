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
@Table(name = "TpaMaster")
public class MasterOtherMasters2_TpaMaster_Entity extends Auditable<String> {



	private String tpaName;
	private String date;
	private String userName;
	private String terms;
	private String discount;
	private String mouExpDate;
	private String remind;

	private String document1;
	private String document2;
	private String document3;
	private String document4;
	private String document5;

	private String companyId;
}
