package com.as.reporsitories.patho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.patho.Patho_PathoLabReport_Entity;
import com.as.response.CountByDate;
import com.as.response.WatingPatientResponse;


public interface Patho_PathoLabReport_Repository extends JpaRepository<Patho_PathoLabReport_Entity, Integer> {
	
	Patho_PathoLabReport_Entity findByIpdno(String ipdno);
	
	List<Patho_PathoLabReport_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
	List<Patho_PathoLabReport_Entity> findByDateAndCreditYearOrderByIdDesc(String date,Long creditYear);
	
	
	Patho_PathoLabReport_Entity findById(Long id);

	@Query("SELECT MAX(cast(a.srno as int))+1 FROM Patho_PathoLabReport_Entity a")
	Long getNextsrno();
	
	/*Waiting Operation Start
	 * */
//	@Query(value="SELECT "
//				+ "a.date AS date, "
//				+ "r.name AS name, "
//				+ "a.uhid AS uhid, "
//				+ "a.ipdno AS ipdno, "
//				+ "r.sex AS sex, "
//				+ "r.age AS age, "
//				+ "c.name AS consultant, "
//				+ "\"PathoRequest\" AS dept, "
//				+ "( "
//					+ "SELECT GROUP_CONCAT(DISTINCT o.test_name SEPARATOR ',') "
//					+ "FROM request_sheet_ipd_details o, request_sheet_ipd s "
//					+ "WHERE s.ipdno = a.ipdno AND o.request_id = s.id) AS testName, "
//				+ "( "
//					+ "SELECT GROUP_CONCAT(o.id SEPARATOR ',') "
//					+ "FROM request_sheet_ipd_details o, request_sheet_ipd s "
//					+ "WHERE s.ipdno = a.ipdno AND o.request_id = s.id) AS testId, "
//				+ "r.mobile "
//			+ "FROM reception_reception_registration r, reception_reception_admission a, consultant_master c "
//			+ "WHERE a.uhid = r.uhid AND c.id = a.consultant1 AND a.date =:date ",nativeQuery=true)
//	List<WatingPatientResponse> getWatingPatientList(@Param("date") String date);
	
	
	
	
	
	/*For PathoRequest
	 * */
	@Query(value="SELECT a.ipdno, a.uhid, "
			+ "(SELECT s.id FROM request_sheet_ipd s WHERE s.id=LAST_INSERT_ID() AND a.ipdno = s.ipdno AND s.id=:billNo) AS billNo, "
			+ "(SELECT  GROUP_CONCAT(o.test_name SEPARATOR ';') FROM request_sheet_ipd_details o, request_sheet_ipd s WHERE s.ipdno = a.ipdno AND o.request_id = s.id AND s.date =:date AND s.id=:billNo) AS testName, "
			+ "(SELECT GROUP_CONCAT(o.id SEPARATOR ';') FROM request_sheet_ipd_details o, request_sheet_ipd s WHERE s.ipdno = a.ipdno AND o.request_id = s.id AND s.date =:date AND s.id=:billNo) AS testId "
			+ "FROM reception_reception_admission a "
			+ "WHERE  a.ipdno =:ipdno AND a.uhid =:uhid ",nativeQuery=true)
	WatingPatientResponse getWatingPatientTestRequestSheet(@Param("uhid") Long uhid, @Param("ipdno") Long ipdno, @Param("date") String date, @Param("billNo") String billNo);
	@Query(value="SELECT a.ipdno, a.uhid, "
			+ "o.test_name AS testName, "
			+ "o.id AS testId "
			+ "FROM reception_reception_admission a, request_sheet_ipd_details o, request_sheet_ipd s "
			+ "WHERE s.credit_year=:creditYear AND s.ipdno = a.ipdno AND o.request_id = s.id AND a.ipdno=:ipdno AND a.uhid =:uhid AND s.date=:date AND s.id=:billNo ",nativeQuery=true)
	List<WatingPatientResponse> getWatingPatientTestArrayRequestSheet(@Param("uhid") Long uhid, @Param("ipdno") Long ipdno, @Param("date") String date, @Param("billNo") String billNo, @Param("creditYear") Long creditYear);
	
	/*For LabBill
	 * */
	@Query(value="SELECT \" \" AS ipdno, a.uhid, "
			+ "(SELECT s.id FROM reception_bill_lab s WHERE s.id=LAST_INSERT_ID() AND a.uhid = s.uhid AND s.date=:date AND s.id=:billNo) AS billNo, "
			+ "(SELECT  GROUP_CONCAT(o.particulars SEPARATOR ';') FROM reception_bill_lab_details o, reception_bill_lab s WHERE s.uhid = a.uhid AND o.bill_no = s.id AND s.date =:date AND s.id=:billNo) AS testName, "
			+ "(SELECT GROUP_CONCAT(o.test_code SEPARATOR ';') FROM reception_bill_lab_details o, reception_bill_lab s WHERE s.uhid = a.uhid AND o.bill_no = s.id AND s.date =:date AND s.id=:billNo) AS testId "
			+ "FROM reception_reception_registration a "
			+ "WHERE a.uhid =:uhid ",nativeQuery=true)
	WatingPatientResponse getWatingPatientTestLabBill(@Param("uhid") Long uhid, @Param("date") String date, @Param("billNo") String billNo);
	@Query(value="SELECT \" \" AS ipdno, a.uhid, "
			+ "o.particulars AS testName, "
			+ "o.id AS testId "
			+ "FROM reception_reception_registration a, reception_bill_lab_details o, reception_bill_lab s "
			+ "WHERE s.credit_year=:creditYear AND s.uhid = a.uhid AND o.bill_no = s.id AND a.uhid =:uhid AND s.date=:date AND s.id=:billNo ",nativeQuery=true)
	List<WatingPatientResponse> getWatingPatientTestArrayLabBill(@Param("uhid") Long uhid, @Param("date") String date, @Param("billNo") String billNo, @Param("creditYear") Long creditYear);
	
	
	
	
	
	/*Waiting Operation End
	 * */
	
	/*Waiting Collected Start 
	 * */
//	@Query(value="SELECT "
//			+ "p.date AS date,"
//			+ "p.patient_name AS name,"
//			+ "p.uhid AS uhid,"
//			+ "p.ipdno AS ipdno,"
//			+ "p.gender AS sex,"
//			+ "p.age AS age,"
//			+ "p.referred_by AS consultant,"
//			+ "p.dept AS dept,"
//			+ "p.test_name AS testName,"
//			+ "p.test_id AS testId,"
//			+ "p.mobile "
//			+ "FROM patho_patient_master p "
//			+"WHERE p.date =:date ",nativeQuery=true)
//	List<WatingPatientResponse> getCollectedPatientList(@Param("date") String date);
	List<Patho_PathoLabReport_Entity> findByDateAndWaitingFlag(String date,boolean flag);
	List<Patho_PathoLabReport_Entity> findByDateAndCollectedFlag(String date,boolean flag);
	List<Patho_PathoLabReport_Entity> findByDateAndReceivedFlag(String date,boolean flag);
	List<Patho_PathoLabReport_Entity> findByDateAndPrintedFlag(String date,boolean flag);
	/*Waiting Collected End 
	 * */
	
	
	@Query(value="SELECT IF(ISNULL(sum(collected_flag)), 0, sum(collected_flag)) AS collected, IF(ISNULL(sum(received_flag)), 0, sum(received_flag)) AS received, IF(ISNULL(sum(printed_flag)), 0, sum(printed_flag)) AS printed from patho_patient_master WHERE date =:date AND credit_year=:creditYear ",nativeQuery=true)
	CountByDate getAllCountByDate(@Param("date") String date, @Param("creditYear") Long creditYear);
}
