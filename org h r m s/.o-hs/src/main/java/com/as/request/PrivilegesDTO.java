package com.as.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrivilegesDTO {
	
	private Integer id;
	
	private Integer i;

	private String menu_name;
	

	private String menu_var;
	

	private Boolean menu_action;
	

	private Boolean edit_action;
	
	private Boolean delete_action;
}
