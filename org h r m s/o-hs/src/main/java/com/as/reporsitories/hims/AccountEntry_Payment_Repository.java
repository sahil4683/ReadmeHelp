package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.AccountEntry_Payment_Entity;
import com.as.response.BillNameBillNoUhid;

public interface AccountEntry_Payment_Repository extends JpaRepository<AccountEntry_Payment_Entity, Integer> {

	AccountEntry_Payment_Entity findByipdno(String ipdno);
	
//	AccountEntry_Payment_Entity findTopByOrderByIdDesc();

	AccountEntry_Payment_Entity findByIdAndCreditYear(Long valueOf,Long yearCd);
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.uhid AS uhid, "
			+ " bill.ipdno AS ipd, "
			+ " bill.payment_no AS billNo, "
			+ " bill.receipt_date AS date "
			+ " FROM account_entry_payment bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.ipdno LIKE :searchtext% OR "
			+ "	bill.payment_no LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	
	List<AccountEntry_Payment_Entity> findByReceiptDateAndCreditYear(String date, Long yearCd);

//	@Query(value="SELECT " + 
//			"b.payment_no AS receiptNo," + 
//			"b.uhid AS uhid," + 
//			"b.ipdno AS ipdNo," + 
//			"b.paid_to AS receivedFrom," + 
//			"r.name AS name," + 
//			"d.dept AS dept," + 
//			"o.organization," + 
//			"b.type AS type," + 
//			"b.amount AS receipt," + 
//			"b.dated AS chequeDetails," + 
//			"b.against AS narration " + 
//			"FROM " + 
//			"account_entry_payment b," + 
//			"reception_reception_registration r," + 
//			"organization_master o," + 
//			"department_master d " + 
//			"WHERE b.credit_year=:creditYear AND b.uhid = r.uhid AND o.id = r.organization AND d.id = b.dept "
//			+ "AND (:fromDate is null or b.receipt_date BETWEEN :fromDate AND :toDate) "
//			
////			+ "(:creditAgainst is null or b.credit_against = :creditAgainst) AND "
////			+ "(:organizationName is null or r.organization = :organizationName) AND "
////			+ "(:userName is null or b.user_name = :userName) AND "
////			+ "(:refBy is null or b.consultant1 = :refBy) AND "
//////			+ "(:billType is null or b.bill_type = :billType) AND "
////			+ "(:subDept is null or b.sub_dept = :subDept) AND "
//////			+ "(:typeOfPatient is null or b.patient_type_old_new = :typeOfPatient) AND "
////			+ "(:consultant is null or b.consultant1 = :consultant)"
//			+ ";", nativeQuery = true)
//	List<ReportBillInterface> getBillReport(
//			@Param("fromDate") String fromDate, 
//			@Param("toDate") String toDate,
//			@Param("creditYear") Long creditYear
////			@Param("creditAgainst") String creditAgainst,
////			@Param("organizationName") String organizationName,
////			@Param("userName") String userName,
////			@Param("refBy") String refBy,
//////			@Param("billType") String billType,
////			@Param("subDept") String subDept,
//////			@Param("typeOfPatient") String typeOfPatient,
////			@Param("consultant") String consultant
//			);
	
}
