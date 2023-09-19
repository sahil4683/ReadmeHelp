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
@Table(name = "IpdPackageMaster")
public class MasterPackageMaster_IpdPackageMaster_Entity extends Auditable<String> {



	private String packageName;
	private String organizationName;
	private String organizationCode;
	private String activeYn;
	private String total;
	private String companyId;

}
