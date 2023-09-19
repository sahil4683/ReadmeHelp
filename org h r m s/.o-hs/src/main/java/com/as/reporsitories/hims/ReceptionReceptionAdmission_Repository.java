package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.response.AdmissionDischargeInterface;
import com.as.response.AdmitedPatientListForShiftingResponse;
import com.as.response.BillNameBillNoUhid;
import com.as.response.ReceptionReceptionAdmission_Entity_Details;

public interface ReceptionReceptionAdmission_Repository extends JpaRepository<ReceptionReceptionAdmission_Entity, Integer> {

	ReceptionReceptionAdmission_Entity findById(Long valueOf);
	

	List<ReceptionReceptionAdmission_Entity> findByUHIDAndCreditYearOrderByIdDesc(String IPDNO,Long creditYear);	
	
	//
	ReceptionReceptionAdmission_Entity findByIPDNOAndCreditYear(String IPDNO,Long creditYear);	
	
	@Query(value="SELECT admit.*, reg.name from reception_reception_admission admit, reception_reception_registration reg WHERE admit.uhid = reg.uhid AND admit.credit_year=:creditYear AND admit.ipdno=:ipdno ;", nativeQuery = true)
	ReceptionReceptionAdmission_Entity_Details findByIPDNOAndCreditYear_Details(@Param("ipdno") String ipdno,@Param("creditYear") Long creditYear);
	//
	
	
	// @Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.creditYear=:creditYear")
	List<Object[]> getFullPatientDetail(@Param("creditYear") Long creditYear);
	// @Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.IPDNO=?1")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.IPDNO=?1 AND a.creditYear=?2")
	List<Object[]> getFullPatientDetailByIPD(String ipd,Long creditYear);
	// @Query(value="SELECT a,r FROM com.as.h_entities.ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.date=?1")
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.date=?1 AND a.creditYear=?2")
	List<Object[]> getFullPatientDetailByDate(String date,Long creditYear);

	
//	@Query("SELECT MAX(a.IPDNO) FROM ReceptionReceptionAdmission_Entity a")
//	Long getNextIPDNO();

	@Query("SELECT MAX(a.IPDNO) FROM ReceptionReceptionAdmission_Entity a")
	Long getNextIPD();
	
	List<ReceptionReceptionAdmission_Entity> findByDischargeAndCreditYear(String disFlag,Long creditYear);
	
	List<ReceptionReceptionAdmission_Entity> findByDate(String date);

	@Query(value="SELECT r.name as name, r.uhid as uhid, a.ipdno as ipd FROM reception_reception_registration r INNER JOIN reception_reception_admission a ON r.uhid = a.uhid AND a.credit_year=?1 ORDER BY ipd DESC;", nativeQuery = true)
	List<BillNameBillNoUhid> getNameUhidIpd(Long creditYear);
	
	@Query(value="SELECT r.name as NAME, a.uhid as uhid, a.ipdno as ipd, a.discharge AS diss FROM reception_reception_registration r, discharge d RIGHT JOIN reception_reception_admission a ON a.ipdno = d.ipdno WHERE r.uhid = a.uhid ORDER BY ipd DESC;", nativeQuery = true)
	List<BillNameBillNoUhid> getDischargeJoinList();
	
	@Query(value="SELECT " + 
			"a.id AS id, " + 
			"a.ipdno AS ipdno, " + 
			"a.date AS date, " + 
			"a.uhid AS uhid," + 
			"r.name AS name, " + 
			"a.bedno AS bedno " + 
			"FROM reception_reception_registration r RIGHT " + 
			"JOIN reception_reception_admission a " + 
			"ON a.uhid = r.uhid " + 
			"WHERE r.uhid = a.uhid AND a.discharge=\"NO\" AND a.credit_year=?1 ORDER BY a.date DESC;", nativeQuery = true)
	List<AdmitedPatientListForShiftingResponse> getAdmitedPatientListForShifting(Long creditYear);
	
	@Query(value="SELECT " + 
			"a.id AS id, " + 
			"a.ipdno AS ipdno, " + 
			"a.date AS date, " + 
			"a.uhid AS uhid," + 
			"r.name AS name, " + 
			"a.bedno AS bedno " + 
			"FROM reception_reception_registration r RIGHT " + 
			"JOIN reception_reception_admission a " + 
			"ON a.uhid = r.uhid " + 
			"WHERE r.uhid = a.uhid AND a.discharge=\"NO\" AND a.ipdno=?1 AND a.credit_year=?2 ORDER BY a.ipdno DESC;", nativeQuery = true)
	AdmitedPatientListForShiftingResponse getAdmitedPatientListForShiftingByIPDNo(String ipdno,Long creditYear);
	
	@Query(value="SELECT r.name as name, a.uhid as uhid, a.ipdno as ipd "
			+ "FROM reception_reception_registration r RIGHT JOIN reception_reception_admission a ON a.uhid = r.uhid "
			+ "WHERE r.uhid = a.uhid "
			+ "AND a.discharge=\"NO\" "
			+ "AND a.credit_year=:creditYear "
			+ "AND r.name LIKE :searchtext% "
			+ "OR a.uhid LIKE :searchtext% "
			+ "OR a.ipdno LIKE :searchtext% "
			+ "ORDER BY a.ipdno DESC;", nativeQuery = true)
	List<BillNameBillNoUhid> getAdmitedPatientByStatus(@Param("searchtext") String searchtext,@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT " + 
			"a.date, " + 
			"a.time, " + 
			"a.uhid as uhid, " + 
			"(SELECT r.name FROM reception_reception_registration r WHERE r.uhid = a.uhid) AS name, " + 
			"a.ipdno as ipd, " + 
			"d.date AS dischargeDate, " + 
			"d.dtime AS dischargeTime, " + 
			"a.age, " +
			"a.sex, " + 
			"a.bedno, " + 
			"d.group_name AS groupname, " + 
			"(SELECT c.name FROM consultant_master c WHERE c.id = a.consultant1) AS consultant, " + 
			"(SELECT o.organization FROM organization_master o WHERE o.id = a.organization) AS organization," + 
			"a.insurance_com, " + 
			"a.discharge AS status " + 
			"FROM discharge d " + 
			"RIGHT JOIN reception_reception_admission a " + 
			"ON a.ipdno = d.ipdno " + 
			"WHERE a.credit_year=:creditYear AND " +
			"(:fromDate is null or a.date BETWEEN :fromDate AND :toDate) AND" +
			"(:patientName is null or a.ipdno = :patientName) AND " +
			"(:organizationName is null or a.organization = :organizationName) AND " +
			"(:typeOfPatient is null or a.typeof_patient = :typeOfPatient) AND " +
			"(:dischargeyn is null or a.discharge = :dischargeyn) AND " +
			"(:consultant is null or a.consultant1 = :consultant) " +
			"ORDER BY ipd DESC;", nativeQuery = true)
	List<AdmissionDischargeInterface> getAdmissionDischarge(
			@Param("fromDate") String fromDate,
			@Param("toDate") String toDate,
			@Param("patientName") String patientName,
			@Param("organizationName") String organizationName,
			@Param("typeOfPatient") String typeOfPatient,
			@Param("dischargeyn") String dischargeyn,
			@Param("consultant") String consultant,
			@Param("creditYear") Long creditYear
			);
	
	
	
	@Query(value="SELECT "
			+ " admit.id AS id, "
			+ " reg.name AS name, "
			+ " admit.ipdno AS ipd, "
			+ " admit.uhid AS uhid, "
			+ " admit.date AS date "
			+ " FROM reception_reception_admission admit "
			+ " JOIN reception_reception_registration reg "
			+ " ON  admit.uhid = reg.uhid "
			+ " WHERE admit.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	admit.ipdno LIKE :searchtext% OR "
			+ "	admit.uhid LIKE :searchtext%  "
			+ " ORDER BY admit.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN ReceptionReceptionAdmission_Entity a ON a.UHID = r.UHID WHERE r.UHID = a.UHID AND a.id=?1 AND a.creditYear=?2")
	List<Object[]> getFullPatientDetailById(Long id,Long creditYear);

}

