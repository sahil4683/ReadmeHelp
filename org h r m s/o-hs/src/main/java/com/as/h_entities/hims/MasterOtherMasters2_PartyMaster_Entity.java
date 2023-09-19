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
@Table(name = "PartyMaster")
public class MasterOtherMasters2_PartyMaster_Entity extends Auditable<String> {



	private String accountName;
	private String groupName;
	private String subGroup;
	private String subGroup1;
	private String subGroup2;
	private String accountOpeningBalance;
	private String accountOpeningBalanceType;
	private String creditDays;
	private String discount;
	private String gstNo;
	private String panNo;
	private String aadharNo;
	private String chequeName;
	private String ifscCode;
	private String accountNo;
	private String accountType;
	private String bankName;
	private String branchName;
	private String branchCode;
	private String address;
	private String address2;
	private String rtgsRemarks;
	private String tdsCategory;
	private String tdsStatus;

	private String companyId;
}
