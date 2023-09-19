package com.as.h_dto.hims;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class IpdBill_FinalIpdBill_DTO {

	private Long id;

	List<IpdBill_FinalIpdBill_Details_DTO> details = new ArrayList<>();

	@NotBlank
	private String uhid;
	private String ipdno;
	private String age;
	private String sex;
	private String admissionDate;
	private String admissionTime;
	private String dischargeDate;
	private String dischargeTime;
	private String totalDays;
	private String vtype;
	private String creditAgainst;
	private String billNo;
	@NotBlank
	private String department;
	@NotBlank
	private String subDept;
	@NotBlank
	private String consultant1;
	private String consultant2;
	private String refBy;
	private String highRisk;
	private String emergency;
	private String total;
	private String lessDeposit;
	private String lessCashlessAuth;
	private String discount;
	private String otherDiscount;
	private String otherDiscountPer;
	private String otherDiscountAmt;
	private String additionalCharges;
	private String additionalChargesPer;
	private String lessChargesPer;
	private String lessCharges;
	private String netTotal;
	private String grandTotal;
	private String date;
	private String time;
	private String totalDue;
	private String creditId;
	private String userName;
	private String dischargeType;
	private String packageName;
	private String remarks;
	private String doroType;// Not Und
	private String serviceName;
	private String servicePer;
	private String serviceOn;
	private String serviceAmt;
	private String TPARECEPITYN;// Not Und

	private String companyId;
}
