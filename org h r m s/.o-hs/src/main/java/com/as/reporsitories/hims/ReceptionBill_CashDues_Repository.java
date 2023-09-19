package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ReceptionBill_CashDues_Entity;
import com.as.response.BillNameBillNoUhid;

public interface ReceptionBill_CashDues_Repository extends JpaRepository<ReceptionBill_CashDues_Entity, Integer> {

	List<ReceptionBill_CashDues_Entity> findByUhidAndCreditYear(String uhid,Long CreditYear);
	
	@Query("SELECT MAX(cast(a.receiptNo as int)) FROM ReceptionBill_CashDues_Entity a")
	Long getNextReceiptNo();

	ReceptionBill_CashDues_Entity findById(Long valueOf);
	
	List<ReceptionBill_CashDues_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
	List<ReceptionBill_CashDues_Entity> findByReceiptDateAndCreditYearOrderByIdDesc(String receiptDate,Long creditYear);
	
	
	@Query(value="SELECT "
			+ " due.id AS id, "
			+ " reg.name AS name, "
			+ " due.bill_no AS billNo, "
			+ " due.uhid AS uhid, "
			+ " due.receipt_date AS date "
			+ " FROM cash_dues due "
			+ " JOIN reception_reception_registration reg "
			+ " ON  due.uhid = reg.uhid "
			+ " WHERE due.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	due.bill_no LIKE :searchtext% OR "
			+ "	due.uhid LIKE :searchtext%  "
			+ " ORDER BY due.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
}
