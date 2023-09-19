package com.as.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "AccountYear")
public class AccountYear_Entity extends Auditable<String> {

	private String fromDate;
	private String toDate;
	private String acYear;
	
	private boolean isDefault;

}
