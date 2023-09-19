package com.as.response;

public interface FeeCollectionInterface {
	
	String getDate();
	
	Integer getIpd_Diposit();
	Integer getIpd_Bill();
	Integer getIpd_Refund();

	Integer getOpd_Diposit();
	Integer getOpd_Bill();
	Integer getOpd_Refund();

	Integer getLab_Diposit();
	Integer getLab_Bill();
	Integer getLab_Refund();

	Integer getRadio_Diposit();
	Integer getRadio_Bill();
	Integer getRadio_Refund();

	Integer getHealthCheckup_Diposit();
	Integer getHealthCheckup_Bill();
	Integer getHealthCheckup_Refund();

	Integer getReceipt_Total();
	Integer getReceipt_Refund();
	Integer getNet_Total();
	
}
