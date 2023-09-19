package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MasterPackageMaster_OpdPackageMaster_DTO {
	private Long id;

	private String packageName;
	private String organizationName;
	private String organizationCode;
	private String activeYn;
	private String total;
	private String companyId;

	List<MasterPackageMaster_OpdPackageMaster_Details_DTO> details=new ArrayList<>();
}
