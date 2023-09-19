package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RequestSheetIpd_DTO {

	private Long id;

	private String ipdno;
	private String uhid;
	private String date;
	private String organization;
	private String type;
	private String pathoFlag;
	private String remarks;
	private String companyId;
	
	List<RequestSheetIpd_Details_DTO> details = new ArrayList<>();
	
}
