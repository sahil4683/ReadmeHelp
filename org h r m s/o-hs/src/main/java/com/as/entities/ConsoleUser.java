package com.as.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "ConsoleUser")
public class ConsoleUser extends Auditable<String> {

//	@NotBlank
	private String username;
	
//	@NotBlank
	private String password;

}