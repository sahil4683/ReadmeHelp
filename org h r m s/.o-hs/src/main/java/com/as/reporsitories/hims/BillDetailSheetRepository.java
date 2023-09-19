package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.BillDetailSheet;
import com.as.response.BillDetailSheetTable;
import com.as.response.BillNameBillNoUhid;

public interface BillDetailSheetRepository extends JpaRepository<BillDetailSheet, Integer> {
	
	BillDetailSheet findById(Long id);

	List<BillDetailSheet> findByIpdnoAndCreditYear(String ipdno, Long creditYear);
	
//	List<BillDetailSheet> findByCreditYearOrderByIdDesc(Long creditYear);
	@Query(value="SELECT bill.*, reg.name FROM bill_detail_sheet bill, reception_reception_registration reg WHERE bill.uhid = reg.uhid AND bill.credit_year=:creditYear ;", nativeQuery = true)
	List<BillDetailSheetTable> findByCreditYearOrderByIdDesc2(@Param("creditYear") Long creditYear);
	
//	List<BillDetailSheet> findByDateAndCreditYearOrderByIdDesc(String date,Long creditYear);
	@Query(value="SELECT bill.*, reg.name FROM bill_detail_sheet bill, reception_reception_registration reg WHERE bill.uhid = reg.uhid AND bill.credit_year=:creditYear AND bill.date=:date ;", nativeQuery = true)
	List<BillDetailSheetTable> findByDateAndCreditYearOrderByIdDesc2(@Param("date") String date,@Param("creditYear") Long creditYear);
	
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.uhid AS uhid, "
			+ " bill.ipdno AS ipd, "
			+ " bill.date AS date "
			+ " FROM bill_detail_sheet bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.ipdno LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT bill.*, reg.name FROM bill_detail_sheet bill, reception_reception_registration reg WHERE bill.uhid = reg.uhid AND bill.credit_year=:creditYear AND bill.id=:id ;", nativeQuery = true)
	BillDetailSheetTable findByIdAndCreditYear(@Param("id") Long id ,@Param("creditYear") Long creditYear);
}

