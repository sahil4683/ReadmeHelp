package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BillDetailDTO {

	private Long id;
	private String uhid;
	private String ipdno;
	private String date;
	private String highRisk;
	private String emergency;
	private String organization;
	private String total;
	private String advance;
	private String netTotal;
	
	private List<ListDetail> listData=new ArrayList<>();;
	List<String> dailyTestList=new ArrayList<>();// For Entity Convert Into String 
	
	private String companyId;
	
}
