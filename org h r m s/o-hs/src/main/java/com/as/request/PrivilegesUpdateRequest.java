package com.as.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrivilegesUpdateRequest {

	private Long user;
	
	private List<PrivilegesDTO> privileges;
	
}
