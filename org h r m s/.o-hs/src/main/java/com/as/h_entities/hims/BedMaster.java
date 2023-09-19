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
@Table(name = "bedmaster")
public class BedMaster extends Auditable<String> {



	private int group_s;
	private String bedno;
	private String remarks;
	private int bookedyn;
	private int rate;
	private String roomType;
	private int specialRate;
	private int semiSpcRate;
	private int deluxRate;
	private int ordinal;
	private String subType;
	private int extrabed;
	private int sequence;
	private int activateYN;
	private String activateDate;
	private int companyId;
	private String wardname;
	private int wardid;
}
