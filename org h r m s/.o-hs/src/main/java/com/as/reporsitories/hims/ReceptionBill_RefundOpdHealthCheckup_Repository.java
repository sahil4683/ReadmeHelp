package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ReceptionBill_RefundOpdHealthCheckup_Entity;
import com.as.response.BillNameBillNoUhid;
import com.as.response.ReportBillInterface;

public interface ReceptionBill_RefundOpdHealthCheckup_Repository extends JpaRepository<ReceptionBill_RefundOpdHealthCheckup_Entity, Integer> {

	ReceptionBill_RefundOpdHealthCheckup_Entity findById(Long id);
	
	@Query("SELECT MAX(cast(a.billNo as int)) FROM ReceptionBill_RefundOpdHealthCheckup_Entity a Order By a.id asc")
	String getNextBillNo();
	
//	ReceptionBill_RefundOpdHealthCheckup_Entity findTopByOrderByIdDesc();
	
//	List<ReceptionBill_RefundOpdHealthCheckup_Entity> findByDate(String date);
	// @Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBill_RefundOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=?1 ORDER BY a.date DESC")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBill_RefundOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=:date AND a.creditYear=:creditYear ORDER BY a.date DESC")
	List<Object[]> getFullByDate(@Param("date") String date,@Param("creditYear") Long creditYear);
	// @Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBill_RefundOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid ORDER BY a.date DESC")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBill_RefundOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.creditYear=:creditYear ORDER BY a.date DESC")
	List<Object[]> getAll(@Param("creditYear") Long creditYear);
	
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.bill_no AS billNo, "
			+ " bill.uhid AS uhid, "
			+ " bill.date AS date "
			+ " FROM reception_bill_refund_opd_health_checkup bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.bill_no LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBill_RefundOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.id=:id AND a.creditYear=:creditYear ORDER BY a.date DESC")
	List<Object[]> getFullById(@Param("id") Long id,@Param("creditYear") Long creditYear);

	@Query(value="SELECT " + 
			"b.DATE AS DATE," + 
			"b.bill_no AS billNo," + 
			"(SELECT sub_dept FROM sub_department_master WHERE id = b.sub_dept) AS subDept," + 
			"b.uhid AS uhid," + 
			"r.name," + 
			"(SELECT NAME FROM doctor_reference WHERE id = b.ref_by1) AS refBy," + 
			"(SELECT NAME FROM consultant_master WHERE id = b.consultant1) AS consultant," + 
			"(SELECT organization FROM organization_master WHERE id = b.organization) AS organization," + 
			"b.concession AS concession," + 
			"b.trust_amount AS trust," + 
			"b.nettotal AS netTotal " + 
			"FROM " + 
			"reception_bill_refund_opd_health_checkup b, " + 
			"reception_reception_registration r " +
			"WHERE b.credit_year=:creditYear AND "
			+ "b.uhid = r.uhid AND "
			+ "(:fromDate is null or b.date BETWEEN :fromDate AND :toDate) AND "
			+ "(:creditAgainst is null or b.credit_against = :creditAgainst) AND "
			+ "(:organizationName is null or b.organization = :organizationName) AND "
			+ "(:userName is null or b.user_name = :userName) AND "
			+ "(:refBy is null or b.ref_by1 = :refBy) AND "
			+ "(:billType is null or b.bill_type = :billType) AND "
			+ "(:subDept is null or b.sub_dept = :subDept) AND "
			+ "(:typeOfPatient is null or b.patient_type_old_new = :typeOfPatient) AND "
			+ "(:modeOfPayment is null or b.method_of_payment = :modeOfPayment) AND "
			+ "(:consultant is null or b.consultant1 = :consultant) ;"
			, nativeQuery = true)
	List<ReportBillInterface> getBillReport(
			@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate,
			@Param("creditAgainst") String creditAgainst,
			@Param("organizationName") String organizationName,
			@Param("userName") String userName,
			@Param("refBy") String refBy,
			@Param("billType") String billType,
			@Param("subDept") String subDept,
			@Param("typeOfPatient") String typeOfPatient,
			@Param("consultant") String consultant,
			@Param("creditYear") Long creditYear,
			@Param("modeOfPayment") String modeOfPayment
			);
	
}
