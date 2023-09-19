package com.as.h_dto.hims;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MasterOtherMasters1_Department_DTO {

	private Long id;

	@NotBlank
	private String dept;
	@NotBlank
	private String prefix;
	private String companyId;

}
