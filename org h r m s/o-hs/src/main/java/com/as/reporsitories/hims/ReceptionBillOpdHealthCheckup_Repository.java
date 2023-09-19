package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ReceptionBillOpdHealthCheckup_Entity;
import com.as.response.BillNameBillNoUhid;
import com.as.response.GroupWiseTestInterface;
import com.as.response.PendingDueInterface;
import com.as.response.ReportBillInterface;

public interface ReceptionBillOpdHealthCheckup_Repository extends JpaRepository<ReceptionBillOpdHealthCheckup_Entity, Integer> {

	ReceptionBillOpdHealthCheckup_Entity findById(Long id);
	
	@Query("SELECT MAX(cast(a.billNo as int)) FROM ReceptionBillOpdHealthCheckup_Entity a Order By a.id asc")
	String getNextBillNo();
	
//	ReceptionBillOpdHealthCheckup_Entity findTopByOrderByIdDesc();
	
//	@Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBillOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=?1 ORDER BY a.date DESC")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBillOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=:date AND a.creditYear=:creditYear ORDER BY a.date DESC")
	List<Object[]> getFullByDate(String date, @Param("creditYear") Long creditYear);
//	@Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBillOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid ORDER BY a.date DESC")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBillOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.creditYear=:creditYear ORDER BY a.date DESC")
	List<Object[]> getAll(@Param("creditYear") Long creditYear);
	
	ReceptionBillOpdHealthCheckup_Entity findByBillNoAndCreditYear(String billNo,Long creditYear);
	
	@Query(value="SELECT " + 
			"r.name as name, " + 
			"r.name as patientName," + 
			"a.uhid AS uhid," + 
			"a.bill_no AS billNo," + 
			"\\\"HEALTHCHECKUP\\\" AS dept," + 
			"\\\"HEALTHCHECKUP\\\" AS subDept," + 
			"a.nettotal AS nettotal," + 
			"a.paid_amount AS paidAmount," + 
			"a.paid_amount AS paid," + 
			"a.due AS due," + 
			"a.date AS date," + 
			"a.total AS total " + 
			"FROM reception_reception_registration r " + 
			"RIGHT JOIN reception_bill_opd_health_checkup a " + 
			"ON r.uhid = a.uhid " + 
			"WHERE CONVERT(a.due,UNSIGNED INTEGER)>0 AND a.credit_year=:creditYear " + 
			"ORDER BY a.uhid DESC;", nativeQuery = true)
	List<PendingDueInterface> getPendingDueList(@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT b.id,r.name as name,b.uhid as uhid,b.bill_no as billNo FROM reception_bill_opd_health_checkup b INNER JOIN reception_reception_registration r ON b.uhid = r.uhid "
			+ "WHERE b.credit_year=:creditYear AND r.name LIKE :searchtext% OR b.uhid LIKE :searchtext% OR b.bill_no LIKE :searchtext% ORDER BY b.bill_no DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> filterUhidNameBillNo(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);

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
			"reception_bill_opd_health_checkup b, " + 
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
	
	@Query(value="SELECT " + 
			"o.uhid," + 
			"o.date," + 
			"o.bill_no AS billno, " + 
			"o.user_name AS operator, " + 
			"r.name, " + 
			"d.particulars AS testName, " + 
			"d.rate, " + 
			"d.procedure_doctor1 AS doctor " + 
			"FROM reception_bill_opd_health_checkup o, reception_bill_opd_health_checkup_details d, reception_reception_registration r " + 
			"WHERE o.credit_year=:creditYear AND o.uhid=r.uhid AND o.id=d.bill_no AND" +
			"(:fromDate is null or o.date BETWEEN :fromDate AND :toDate) AND" +
			"(:groupName is null or d.group_name = :groupName) AND" +
			"(:testName is null or  d.particulars = :testName) AND" +
			"(:doctorName is null or o.consultant1 = :doctorName) " +
			"ORDER By d.procedure_doctor1;", nativeQuery = true)
	List<GroupWiseTestInterface> getGroupWiseTest(
			@Param("fromDate") String fromDate, 
			@Param("toDate") String toDate, 
			@Param("groupName") String groupName,
			@Param("testName") String testName,
			@Param("doctorName") String doctorName,
			@Param("creditYear") Long creditYear
			);
	
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.bill_no AS billNo, "
			+ " bill.uhid AS uhid, "
			+ " bill.date AS date "
			+ " FROM reception_bill_opd_health_checkup bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	bill.bill_no LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionBillOpdHealthCheckup_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.id=:id AND a.creditYear=:creditYear")
	List<Object[]> getFullById(Long id, @Param("creditYear") Long creditYear);
	
}
