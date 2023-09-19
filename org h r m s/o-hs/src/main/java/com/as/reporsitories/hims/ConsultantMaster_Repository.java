package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ConsultantMaster_Entity;
import com.as.response.TransactedDoctorResponse;

public interface ConsultantMaster_Repository extends JpaRepository<ConsultantMaster_Entity, Integer> {

	ConsultantMaster_Entity findById(Long id);
	
	ConsultantMaster_Entity findByName(String name);
	
	@Query(value="SELECT DISTINCT raw.consultant1 AS doctorId, dr.name AS doctorName FROM reception_bill_opd raw, consultant_master dr WHERE raw.consultant1=dr.id AND raw.DATE BETWEEN :fromDate AND :toDate AND raw.credit_year=:creditYear "
			+ "UNION "
			+ "SELECT DISTINCT raw.consultant1 AS doctorId, dr.name AS doctorName FROM reception_bill_lab raw, consultant_master dr WHERE raw.consultant1=dr.id AND raw.DATE BETWEEN :fromDate AND :toDate AND raw.credit_year=:creditYear "
			+ "UNION "
			+ "SELECT DISTINCT raw.consultant1 AS doctorId, dr.name AS doctorName FROM reception_bill_radio raw, consultant_master dr WHERE raw.consultant1=dr.id AND raw.DATE BETWEEN :fromDate AND :toDate AND raw.credit_year=:creditYear "
			+ "UNION "
			+ "SELECT DISTINCT dr.id AS doctorId, dr.name AS doctorName FROM ipd_final_ipd_bill raw, consultant_master dr WHERE raw.consultant1=dr.name AND raw.DATE BETWEEN :fromDate AND :toDate AND raw.credit_year=:creditYear "
			+ "UNION "
			+ "SELECT DISTINCT raw.consultant1 AS doctorId, dr.name AS doctorName FROM reception_bill_opd_health_checkup raw, consultant_master dr WHERE raw.consultant1=dr.id AND raw.DATE BETWEEN :fromDate AND :toDate AND raw.credit_year=:creditYear ;", nativeQuery = true)
	List<TransactedDoctorResponse> getTransactedDoctorList(
				@Param("fromDate") String fromDate,
				@Param("toDate") String toDate, 
				@Param("creditYear") Long creditYear
			);
	
	/*
	 *  Teporary Commented
	 * 
	 * */
	
//	@Query(value=""
//			+ "SELECT raw.uhid, raw.bill_no AS billNo, \"NA\" AS ipdno, raw.date, reg.name AS patientName, \"OPD\" AS typeOfPatient, \" \" AS patientType, org.organization, details.group_name AS groupName, details.particulars, IF(ISNULL(details.amount), 0, details.amount) AS total, IF(ISNULL(details.rate), 0, details.rate) AS netTotal, IF(ISNULL(raw.trust_amount), 0, raw.trust_amount)  AS incentive "
//			+ "FROM reception_bill_opd raw LEFT JOIN reception_bill_opd_details details ON raw.id=details.bill_no , reception_reception_registration reg, organization_master org "
//			+ "WHERE raw.uhid = reg.uhid AND org.id = raw.organization AND raw.date BETWEEN :fromDate AND :toDate AND consultant1=:consultant "
//			+ "UNION "
//			+ "SELECT raw.uhid, raw.bill_no AS billNo, \"NA\" AS ipdno, raw.date, reg.name AS patientName, \"LAB\" AS typeOfPatient, \" \" AS patientType, org.organization, details.group_name AS groupName, details.particulars, IF(ISNULL(details.amount), 0, details.amount) AS total, IF(ISNULL(details.rate), 0, details.rate) AS netTotal, IF(ISNULL(raw.trust_amount), 0, raw.trust_amount)  AS incentive "
//			+ "FROM reception_bill_lab raw LEFT JOIN reception_bill_lab_details details ON raw.id=details.bill_no , reception_reception_registration reg, organization_master org "
//			+ "WHERE raw.uhid = reg.uhid AND org.id = raw.organization AND raw.date BETWEEN :fromDate AND :toDate AND consultant1=:consultant "
//			+ "UNION "
//			+ "SELECT raw.uhid, raw.bill_no AS billNo, \"NA\" AS ipdno, raw.date, reg.name AS patientName, \"RADIO\" AS typeOfPatient, \" \" AS patientType, org.organization, details.group_name AS groupName, details.particulars, IF(ISNULL(details.amount), 0, details.amount) AS total, IF(ISNULL(details.rate), 0, details.rate) AS netTotal, IF(ISNULL(raw.trust_amount), 0, raw.trust_amount)  AS incentive "
//			+ "FROM reception_bill_radio raw LEFT JOIN reception_bill_radio_details details ON raw.id=details.bill_no , reception_reception_registration reg, organization_master org "
//			+ "WHERE raw.uhid = reg.uhid AND org.id = raw.organization AND raw.date BETWEEN :fromDate AND :toDate AND consultant1=:consultant "
//			+ "UNION "
//			+ "SELECT raw.uhid, raw.bill_no AS billNo, \"NA\" AS ipdno, raw.date, reg.name AS patientName, \"HEALTHCHECKUP\" AS typeOfPatient, \" \" AS patientType, org.organization, details.group_name AS groupName, details.particulars, IF(ISNULL(details.amount), 0, details.amount) AS total, IF(ISNULL(details.rate), 0, details.rate) AS netTotal, IF(ISNULL(raw.trust_amount), 0, raw.trust_amount)  AS incentive "
//			+ "FROM reception_bill_opd_health_checkup raw LEFT JOIN reception_bill_opd_health_checkup_details details ON raw.id=details.bill_no , reception_reception_registration reg, organization_master org "
//			+ "WHERE raw.uhid = reg.uhid AND org.id = raw.organization AND raw.date BETWEEN :fromDate AND :toDate AND consultant1=:consultant "
//			+ "UNION "
//			+ "SELECT raw.uhid, raw.bill_no, raw.ipdno AS ipdno, raw.date, reg.name, \"Direct\" AS typeOfPatient,admit.patient_type AS patientType, org.organization, details.group_name AS groupName, details.test_name, IF(ISNULL(details.amount), 0, details.amount) AS total, 0 AS netTotal, 0  AS incentive "
//			+ "FROM ipd_final_ipd_bill raw LEFT JOIN ipd_final_ipd_bill_details details ON raw.id=details.ipd_bill_no , reception_reception_registration reg, organization_master org, reception_reception_admission admit "
//			+ "WHERE admit.uhid=raw.uhid AND raw.uhid = reg.uhid AND org.id = details.orgcode AND raw.date BETWEEN :fromDate AND :toDate AND admit.consultant1=:consultant "
//			+ "ORDER BY date ;", nativeQuery = true)
//	List<TransactedDoctorResponse> getTransactedDoctorData(
//				@Param("fromDate") String fromDate,
//				@Param("toDate") String toDate,
//				@Param("consultant") String consultant
//			);
	@Query(value=""
			+ "SELECT raw.bill_no AS billNo,org.organization,raw.bill_no, raw.uhid, raw.ipdno, raw.date, reg.name AS patientName, details.request_type AS typeOfPatient, details.group_name AS groupName, details.test_name AS particulars, IF(ISNULL(details.amount), 0, details.amount) AS total, 0 AS netTotal, 0  AS incentive "
			+ "FROM "
			+ "ipd_final_ipd_bill raw "
			+ "LEFT JOIN ipd_final_ipd_bill_details details "
			+ "ON raw.id=details.ipd_bill_no , reception_reception_registration reg, organization_master org "
			+ "WHERE org.id = details.orgcode "
			+ "AND raw.uhid = reg.uhid "
			+ "AND details.request_type IN ('Direct','BillRequest') "
			+ "AND raw.date BETWEEN :fromDate AND :toDate "
			+ "AND raw.consultant1=:consultant "
			+ "AND raw.credit_year=:creditYear "
			+ "ORDER BY DATE ;", nativeQuery = true)
	List<TransactedDoctorResponse> getTransactedDoctorData(
				@Param("fromDate") String fromDate,
				@Param("toDate") String toDate,
				@Param("consultant") String consultant,
				@Param("creditYear") Long creditYear
			);
}
