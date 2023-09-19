package com.as.response;

public interface MisInterface {

	/* A Section
	*/
	Integer getNewOpdBillPatients();
	Integer getOldOpdBillPatients();
	Integer getIPDPatients();
	Integer getIPDDischarge();
	Integer getTotalOpdBillPatients();
	Integer getIPDTotal();
	Integer getTotalLabBillPatients();
	Integer getTotalRadioBillPatients();
	Integer getTotalhealthCheckupBillPatients();
	
	/* B Section
	*/
	Integer getTotalAdmission();
	Integer getTotalDischarge();
	Integer getTotalDeath();
	
	/* C Section
	*/
	Integer getSumOpdBill();
	Integer getSumOpdRefund();
	Integer getSumLabBill();
	Integer getSumLabRefund();
	Integer getSumRadioBill();
	Integer getSumRadioRefund();
	Integer getSumIpdBill();
	Integer getSumHealthCheckupBill();
	Integer getSumHealthCheckupRefund();
	Integer getTotalSumBill();
	
	/* D Section
	*/
	Integer getSumOpdBillCash();
	Integer getSumOpdBillCheque();
	Integer getSumOpdBillPlasticMoney();
	Integer getSumOpdDepositCash();
	Integer getSumOpdDepositCheque();
	Integer getSumOpdDepositPlasticMoney();
	Integer getSumOpdRefundCash();
	Integer getSumOpdRefundCheque();
	Integer getSumOpdRefundPlasticMoney();
	
	/* E Section
	*/
	Integer getSumHealthCheckupBillCash();
	Integer getSumHealthCheckupBillCheque();
	Integer getSumHealthCheckupBillPlasticMoney();
	Integer getSumHealthCheckupDepositCash();
	Integer getSumHealthCheckupDepositCheque();
	Integer getSumHealthCheckupDepositPlasticMoney();
	Integer getSumHealthCheckupRefundCash();
	Integer getSumHealthCheckupRefundCheque();
	Integer getSumHealthCheckupRefundPlasticMoney();
	
	/* F Section
	*/
	Integer getSumLabBillCash();
	Integer getSumLabBillCheque();
	Integer getSumLabBillPlasticMoney();
	Integer getSumLabDepositCash();
	Integer getSumLabDepositCheque();
	Integer getSumLabDepositPlasticMoney();
	Integer getSumLabRefundCash();
	Integer getSumLabRefundCheque();
	Integer getSumLabRefundPlasticMoney();
	
	/* G Section
	*/
	Integer getSumRadioBillCash();
	Integer getSumRadioBillCheque();
	Integer getSumRadioBillPlasticMoney();
	Integer getSumRadioDepositCash();
	Integer getSumRadioDepositCheque();
	Integer getSumRadioDepositPlasticMoney();
	Integer getSumRadioRefundCash();
	Integer getSumRadioRefundCheque();
	Integer getSumRadioRefundPlasticMoney();
	
	/* H Section
	*/
	Integer getSumIpdBillCash();
	Integer getSumIpdBillCheque();
	Integer getSumIpdBillPlasticMoney();
	Integer getSumIpdDepositCash();
	Integer getSumIpdDepositCheque();
	Integer getSumIpdDepositPlasticMoney();
	Integer getSumIpdRefundCash();
	Integer getSumIpdRefundCheque();
	Integer getSumIpdRefundPlasticMoney();
	
	
	Integer getTotalCash();
	Integer getTotalCheque();
	Integer getTotalPlasticMoney();
	
	Integer getTotalRefundCash();
	Integer getTotalRefundCheque();
	Integer getTotalRefundPlasticMoney();
	
	Integer getNetReceiptCash();
	Integer getNetReceiptCheque();
	Integer getNetReceiptPlasticMoney();


}
