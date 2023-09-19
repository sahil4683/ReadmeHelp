package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.AccountEntry_Receipt_Entity;
import com.as.response.BillNameBillNoUhid;
import com.as.response.FeeCollectionInterface;
import com.as.response.MisInterface;
import com.as.response.ReportBillInterface;

public interface AccountEntry_Receipt_Repository extends JpaRepository<AccountEntry_Receipt_Entity, Integer> {

	AccountEntry_Receipt_Entity findByIpdno(String ipdno);
	
//	AccountEntry_Receipt_Entity findTopByOrderByIdDesc();

	AccountEntry_Receipt_Entity findByIdAndCreditYear(Long valueOf, Long creditYear);
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.uhid AS uhid, "
			+ " bill.ipdno AS ipd, "
			+ " bill.receipt_no AS billNo, "
			+ " bill.receipt_date AS date "
			+ " FROM account_entry_receipt bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.ipdno LIKE :searchtext% OR "
			+ "	bill.receipt_no LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);

	List<AccountEntry_Receipt_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	List<AccountEntry_Receipt_Entity> findByReceiptDateAndCreditYear(String date,Long creditYear);
	
	
	@Query("SELECT SUM(a.amount) FROM AccountEntry_Receipt_Entity a WHERE a.dept=?1")
	Long sumByDept(String dept);
	
	@Query("SELECT COALESCE(SUM(amount),0) FROM AccountEntry_Receipt_Entity a WHERE a.ipdno=?1 AND a.creditYear=?2")
	String sumByIpdno(String ipdno, Long credit_year);
	
	@Query(value="SELECT" +
			"?1 AS \"Date\"," +
			
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?2 AND dept = \"16\" AND part=\"Advance\" AND receipt_date BETWEEN ?1 AND ?1 ) " + 
			"AS \"IPD_Diposit\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?2 AND dept = \"16\" AND part=\"Full\" AND receipt_date BETWEEN ?1 AND ?1 ) " + 
			"AS \"IPD_Bill\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_payment WHERE credit_year=?2 AND dept = \"16\" AND receipt_date BETWEEN ?1 AND ?1 AND against=\"refund\" )" + 
			"AS \"IPD_Refund\"," + 
			
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?2 AND dept = \"17\" AND receipt_date BETWEEN ?1 AND ?1 ) " + 
			"AS \"OPD_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_opd WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"OPD_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"OPD_Refund\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?2 AND dept = \"84\" AND receipt_date BETWEEN ?1 AND ?1 ) " + 
			"AS \"LAB_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_lab WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"LAB_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_lab WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"LAB_Refund\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?2 AND dept = \"85\" AND receipt_date BETWEEN ?1 AND ?1  ) " + 
			"AS \"Radio_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_radio WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"Radio_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_radio WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"Radio_Refund\"," + 
			"0 AS \"HealthCheckup_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_opd_health_checkup WHERE credit_year=?2 AND date BETWEEN ?1 AND ?1) " + 
			"AS \"HealthCheckup_Bill\"," + 
			"0 AS \"HealthCheckup_Refund\"," + 
			"(SELECT " + 
			"COALESCE(SUM(IPD_Diposit),0)+COALESCE(SUM(IPD_Bill),0)+" + 
			"COALESCE(SUM(OPD_Diposit),0)+COALESCE(SUM(OPD_Bill),0)+" + 
			"COALESCE(SUM(LAB_Diposit),0)+COALESCE(SUM(LAB_Bill),0)+" + 
			"COALESCE(SUM(Radio_Diposit),0)+COALESCE(SUM(Radio_Bill),0)+" + 
			"COALESCE(SUM(HealthCheckup_Diposit),0)+COALESCE(SUM(HealthCheckup_Bill),0)" + 
			") AS \"Receipt_Total\"," + 
			"(SELECT " + 
			"COALESCE(SUM(IPD_Refund),0)+COALESCE(SUM(OPD_Refund),0)+" + 
			"COALESCE(SUM(LAB_Refund),0)+COALESCE(SUM(Radio_Refund),0)+" + 
			"COALESCE(SUM(HealthCheckup_Refund),0)" + 
			") AS \"Receipt_Refund\"," + 
			"(SELECT " + 
			"COALESCE(SUM(Receipt_Total),0)+COALESCE(SUM(OPD_Refund),0)-" + 
			"COALESCE(SUM(Receipt_Refund),0)+COALESCE(SUM(Radio_Refund),0)" + 
			") AS \"Net_Total\"" + 
			";", nativeQuery = true)
	FeeCollectionInterface getFeeCollectionDateWise(String date, Long creditYear);
	
	
	@Query(value="SELECT" +
			"?1 AS \"Date\"," +
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?3 AND dept = \"16\" AND part=\"Advance\" AND receipt_date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"IPD_Diposit\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?3 AND dept = \"16\" AND part=\"Full\" AND receipt_date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"IPD_Bill\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_payment WHERE credit_year=?3 AND dept = \"16\" AND receipt_date BETWEEN ?1 AND ?1 AND against=\"refund\" AND user_name = ?2) " + 
			"AS \"IPD_Refund\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?3 AND dept = \"17\" AND receipt_date BETWEEN ?1 AND ?1 AND against=\"deposit\"  AND user_name = ?2) " + 
			"AS \"OPD_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_opd WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"OPD_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"OPD_Refund\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?3 AND dept = \"84\" AND receipt_date BETWEEN ?1 AND ?1 AND against=\"deposit\" AND user_name = ?2) " + 
			"AS \"LAB_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_lab WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"LAB_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_lab WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"LAB_Refund\"," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=?3 AND dept = \"85\" AND receipt_date BETWEEN ?1 AND ?1 AND against=\"deposit\" AND user_name = ?2) " + 
			"AS \"Radio_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_radio WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"Radio_Bill\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_radio WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"Radio_Refund\"," + 
			"0 AS \"HealthCheckup_Diposit\"," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_opd_health_checkup WHERE credit_year=?3 AND date BETWEEN ?1 AND ?1 AND user_name = ?2) " + 
			"AS \"HealthCheckup_Bill\"," + 
			"0 AS \"HealthCheckup_Refund\"," + 
			"(SELECT " + 
			"COALESCE(SUM(IPD_Diposit),0)+COALESCE(SUM(IPD_Bill),0)+" + 
			"COALESCE(SUM(OPD_Diposit),0)+COALESCE(SUM(OPD_Bill),0)+" + 
			"COALESCE(SUM(LAB_Diposit),0)+COALESCE(SUM(LAB_Bill),0)+" + 
			"COALESCE(SUM(Radio_Diposit),0)+COALESCE(SUM(Radio_Bill),0)+" + 
			"COALESCE(SUM(HealthCheckup_Diposit),0)+COALESCE(SUM(HealthCheckup_Bill),0)" + 
			") AS \"Receipt_Total\"," + 
			"(SELECT " + 
			"COALESCE(SUM(IPD_Refund),0)+COALESCE(SUM(OPD_Refund),0)+" + 
			"COALESCE(SUM(LAB_Refund),0)+COALESCE(SUM(Radio_Refund),0)+" + 
			"COALESCE(SUM(HealthCheckup_Refund),0)" + 
			") AS \"Receipt_Refund\"," + 
			"(SELECT " + 
			"COALESCE(SUM(Receipt_Total),0)+COALESCE(SUM(OPD_Refund),0)-" + 
			"COALESCE(SUM(Receipt_Refund),0)+COALESCE(SUM(Radio_Refund),0)" + 
			") AS \"Net_Total\"" + 
			";", nativeQuery = true)
	FeeCollectionInterface getFeeCollectionDateWiseAndUser(
			@Param("?1") String date, 
			@Param("?2") String userName,
			@Param("?3") Long creditYear
);
	
	
	
	@Query(value="SELECT" + 
			"" + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_opd WHERE credit_year=:creditYear AND patient_type_old_new=\"New\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS NewOpdBillPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_opd WHERE credit_year=:creditYear AND patient_type_old_new=\"Old\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS OldOpdBillPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_reception_admission WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS IPDPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM discharge WHERE credit_year=:creditYear AND dtype=\"DISCHARGE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS IPDDischarge," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_opd WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalOpdBillPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM ipd_final_ipd_bill WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS IPDTotal," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_lab WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalLabBillPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_radio WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalRadioBillPatients," + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_bill_opd_health_checkup WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalhealthCheckupBillPatients," + 
			"" + 
			"(SELECT COALESCE(COUNT(id),0) FROM reception_reception_admission WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalAdmission," + 
			"(SELECT COALESCE(COUNT(id),0) FROM discharge WHERE credit_year=:creditYear AND dtype=\"DISCHARGE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalDischarge," + 
			"(SELECT COALESCE(COUNT(id),0) FROM discharge WHERE credit_year=:creditYear AND dtype=\"EXPIRED\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS TotalDeath," + 
			"" +
//			TOTAL
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_opd WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdBill," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_refund_opd WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdRefund," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_lab WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabBill," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_refund_lab WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabRefund," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_radio WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioBill," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_refund_radio WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioRefund," +
			"(SELECT COALESCE(SUM(grand_total),0) FROM ipd_final_ipd_bill WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumIpdBill," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_opd_health_checkup WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupBill," + 
			"(SELECT COALESCE(SUM(nettotal),0) FROM reception_bill_refund_opd_health_checkup WHERE credit_year=:creditYear AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupRefund," + 
			"(SELECT SumOpdBill-SumOpdRefund + SumLabBill - SumLabRefund + SumRadioBill - SumRadioRefund + SumIpdBill + SumHealthCheckupBill - SumHealthCheckupRefund ) AS TotalSumBill," + 
			"" + 
//			OPD

"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueOpdBillCash," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueOpdBillCheque," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueOpdBillPlasticMoney," +
			"(SELECT COALESCE(SUM(paid_amount),0) + SumDueOpdBillCash FROM reception_bill_opd WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdBillCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0) + SumDueOpdBillCheque FROM reception_bill_opd WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdBillCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0) + SumDueOpdBillPlasticMoney FROM reception_bill_opd WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdBillPlasticMoney," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"17\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumOpdDepositCash," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"17\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumOpdDepositCheque," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"17\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumOpdDepositPlasticMoney," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdRefundCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdRefundCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumOpdRefundPlasticMoney," + 
			"" +
//			HEALTH CHECKUP
			
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"HEALTHCHECKUP\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueHealthCheckupBillCash," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"HEALTHCHECKUP\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueHealthCheckupBillCheque," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"OPD\" AND type=\"HEALTHCHECKUP\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueHealthCheckupBillPlasticMoney," +
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueHealthCheckupBillCash FROM reception_bill_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupBillCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueHealthCheckupBillCheque FROM reception_bill_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupBillCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueHealthCheckupBillPlasticMoney FROM reception_bill_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupBillPlasticMoney," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"82\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupDepositCash," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"82\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupDepositCheque," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"82\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupDepositPlasticMoney," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupRefundCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupRefundCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_opd_health_checkup WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumHealthCheckupRefundPlasticMoney," + 
			"" + 
//			LAB
			
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"LAB\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueLabBillCash," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"LAB\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueLabBillCheque," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"LAB\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueLabBillPlasticMoney," +
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueLabBillCash FROM reception_bill_lab WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabBillCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueLabBillCheque FROM reception_bill_lab WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabBillCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueLabBillPlasticMoney FROM reception_bill_lab WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabBillPlasticMoney," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumLabDepositCash," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumLabDepositCheque," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumLabDepositPlasticMoney," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_lab WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabRefundCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_lab WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabRefundCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_lab WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumLabRefundPlasticMoney," + 
			"" + 
//			RADIO
			
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"RADIO\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueRadioBillCash," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"RADIO\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueRadioBillCheque," +
"(SELECT COALESCE(SUM(amount),0) FROM cash_dues WHERE credit_year=:creditYear AND dept=\"RADIO\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumDueRadioBillPlasticMoney," +
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueRadioBillCash FROM reception_bill_radio WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioBillCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueRadioBillCheque FROM reception_bill_radio WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioBillCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0)+SumDueRadioBillPlasticMoney FROM reception_bill_radio WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioBillPlasticMoney," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumRadioDepositCash," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumRadioDepositCheque," + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"84\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumRadioDepositPlasticMoney," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_radio WHERE credit_year=:creditYear AND method_of_payment=\"Cash\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioRefundCash," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_radio WHERE credit_year=:creditYear AND method_of_payment=\"CHEQUE\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioRefundCheque," + 
			"(SELECT COALESCE(SUM(paid_amount),0) FROM reception_bill_refund_radio WHERE credit_year=:creditYear AND method_of_payment=\"PLASTICMONEY\" AND (:fromDate is null or date BETWEEN :fromDate AND :toDate)) AS SumRadioRefundPlasticMoney," + 
			"" + 
//			IPD
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"Cash\" AND part=\"Full\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdBillCash, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"CHEQUE\" AND part=\"Full\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdBillCheque, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"PLASTICMONEY\" AND part=\"Full\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdBillPlasticMoney, " +
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"Cash\" AND part=\"Advance\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdDepositCash, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"CHEQUE\" AND part=\"Advance\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdDepositCheque, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_receipt WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"PLASTICMONEY\" AND part=\"Advance\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdDepositPlasticMoney, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_payment WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"Cash\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdRefundCash, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_payment WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"CHEQUE\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdRefundCheque, " + 
			"(SELECT COALESCE(SUM(amount),0) FROM account_entry_payment WHERE credit_year=:creditYear AND dept=\"16\" AND type=\"PLASTICMONEY\" AND (:fromDate is null or receipt_date BETWEEN :fromDate AND :toDate)) AS SumIpdRefundPlasticMoney, " +
			"" +
//			FINAL
			"(SELECT SumOpdBillCash+SumHealthCheckupBillCash+SumLabBillCash+SumRadioBillCash+SumIpdBillCash+SumIpdDepositCash ) AS TotalCash," + 
			"(SELECT SumOpdBillCheque+SumHealthCheckupBillCheque+SumLabBillCheque+SumRadioBillCheque+SumIpdBillCheque+SumIpdDepositCheque ) AS TotalCheque," + 
			"(SELECT SumOpdBillPlasticMoney+SumHealthCheckupBillPlasticMoney+SumLabBillPlasticMoney+SumRadioBillPlasticMoney+SumIpdBillPlasticMoney+SumIpdDepositPlasticMoney) AS TotalPlasticMoney," + 
			"" + 
			"(SELECT SumOpdRefundCash+SumHealthCheckupRefundCash+SumLabRefundCash+SumRadioRefundCash+SumIpdRefundCash ) AS TotalRefundCash," + 
			"(SELECT SumOpdRefundCheque+SumHealthCheckupRefundCheque+SumLabRefundCheque+SumRadioRefundCheque+SumIpdRefundCheque ) AS TotalRefundCheque," + 
			"(SELECT SumOpdRefundPlasticMoney+SumHealthCheckupRefundPlasticMoney+SumLabRefundPlasticMoney+SumRadioRefundPlasticMoney+SumIpdRefundPlasticMoney ) AS TotalRefundPlasticMoney," + 
			"" + 
			"(SELECT  TotalCash - TotalRefundCash ) AS NetReceiptCash," + 
			"(SELECT TotalCheque - TotalRefundCheque ) AS NetReceiptCheque," + 
			"(SELECT TotalPlasticMoney - TotalRefundPlasticMoney ) AS NetReceiptPlasticMoney" + 
			";" + 
			"", nativeQuery = true)
	MisInterface getMis(
			@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate,
			@Param("creditYear") Long creditYear
			);
	
	
	@Query(value="SELECT " + 
			"b.receipt_no AS receiptNo," + 
			"b.uhid AS uhid," + 
			"b.ipdno AS ipdNo," + 
			"b.received_from AS receivedFrom," + 
			"r.name AS name," + 
			"d.dept AS dept," + 
			"o.organization," + 
			"b.type AS type," + 
			"b.amount AS receipt," + 
			"b.dated AS chequeDetails," + 
			"b.against AS narration " + 
			"FROM " + 
			"account_entry_receipt b," + 
			"reception_reception_registration r," + 
			"organization_master o," + 
			"department_master d " + 
			"WHERE b.credit_year=:creditYear "
			+ "AND b.uhid = r.uhid "
			+ "AND o.id = r.organization "
			+ "AND d.id = b.dept "
			+ "AND (:fromDate is null or b.receipt_date BETWEEN :fromDate AND :toDate) AND "
			+ "(:type is null or b.type = :type) AND "
			+ "(:part is null or b.part = :part) AND "
			+ "(:toAccount is null or b.to_account = :toAccount) AND "
			+ "(:dept is null or d.id = :dept);"
			, nativeQuery = true)
	List<ReportBillInterface> getBillReport(
			@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate,
			@Param("creditYear") Long creditYear,
			@Param("type") String type,
			@Param("part") String part,
			@Param("toAccount") String toAccount,
			@Param("dept") String dept
			);
	

}
