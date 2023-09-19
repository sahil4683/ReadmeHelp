package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.IpdBill_FinalIpdBill_Entity;
import com.as.response.AdmitedPatientListForShiftingResponse;
import com.as.response.BillNameBillNoUhid;
import com.as.response.ReportBillInterface;

public interface IpdBill_FinalIpdBill_Repository extends JpaRepository<IpdBill_FinalIpdBill_Entity, Integer> {
	
	IpdBill_FinalIpdBill_Entity findByIpdnoAndCreditYear(String ipdno, Long creditYear);
	
	IpdBill_FinalIpdBill_Entity findById(Long id);

	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_FinalIpdBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.creditYear=:creditYear ")
	List<Object[]> findByAllTable(@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_FinalIpdBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=:date AND a.creditYear=:creditYear ")
	List<Object[]> findByAllTableByDate(@Param("date") String date,@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT " + 
			"b.DATE AS date," + 
			"b.bill_no AS billNo," + 
			"b.ipdno AS ipdNo," + 
			"r.name AS name," + 
			"b.consultant1 AS refBy," + 
			"o.organization AS organization," + 
			"b.package_name AS package," + 
			"b.total AS total," + 
			"b.less_deposit AS advance," + 
			"b.discount AS discount," + 
			"b.other_discount AS otherDiscount," + 
			"b.grand_total AS grandTotal " + 
			"FROM " + 
			"ipd_final_ipd_bill b," + 
			"reception_reception_registration r," + 
			"organization_master o " + 
			"WHERE b.credit_year=:creditYear AND b.uhid = r.uhid AND o.id = r.organization "
			+ "AND (:fromDate is null or b.date BETWEEN :fromDate AND :toDate) AND "
			+ "(:creditAgainst is null or b.credit_against = :creditAgainst) AND "
			+ "(:organizationName is null or r.organization = :organizationName) AND "
			+ "(:userName is null or b.user_name = :userName) AND "
			+ "(:refBy is null or b.ref_by = :refBy) AND "
			+ "(:subDept is null or b.sub_dept = :subDept) AND "
			+ "(:consultant is null or b.consultant1 = :consultant)"
			+ ";", nativeQuery = true)
	List<ReportBillInterface> getBillReport(
			@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate,
			@Param("creditAgainst") String creditAgainst,
			@Param("organizationName") String organizationName,
			@Param("userName") String userName,
			@Param("refBy") String refBy,
			@Param("subDept") String subDept,
			@Param("consultant") String consultant,
			@Param("creditYear") Long creditYear
			);
	
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.bill_no AS billNo, "
			+ " bill.uhid AS uhid, "
			+ " bill.ipdno AS ipd, "
			+ " bill.date AS date "
			+ " FROM ipd_final_ipd_bill bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.bill_no LIKE :searchtext% OR "
			+ "	bill.ipdno LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_FinalIpdBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.id=:id AND a.creditYear=:creditYear ")
	List<Object[]> findByAllTableById(@Param("id") Long id,@Param("creditYear") Long creditYear);
	
	
	
	@Query(value="SELECT "
			+ "a.id AS id, "
			+ "a.ipdno AS ipdno, "
			+ "a.date AS DATE, "
			+ "a.uhid AS uhid, "
			+ "r.name AS name, "
			+ "a.bedno AS bedno "
			+ "FROM reception_reception_registration r, reception_reception_admission a, ipd_final_ipd_bill bill "
			+ "WHERE r.uhid = a.uhid AND a.ipdno=bill.ipdno AND bill.ready_for_discharge=?1 AND a.credit_year=?2 "
			+ "ORDER BY a.date DESC;", nativeQuery = true)
	List<AdmitedPatientListForShiftingResponse> getReadyForDischarge(String check,Long creditYear);
	
}
