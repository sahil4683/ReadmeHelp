package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.RequestSheetIpd_Entity;

public interface RequestSheetIpd_Repository extends JpaRepository<RequestSheetIpd_Entity, Integer> {
	
	RequestSheetIpd_Entity findById(Long id);
	
	List<RequestSheetIpd_Entity> findByIpdnoAndCreditYear(String ipdno,Long creditYear);
	
	List<RequestSheetIpd_Entity> findByDateAndCreditYear(String date,Long creditYear);
	
//	List<RequestSheetIpd_Entity> findByIpdnoAndDate(String ipdno, String date);
	
	List<RequestSheetIpd_Entity> findByIpdnoAndTypeAndCreditYear(String ipdno, String type,Long creditYear);
	
	List<RequestSheetIpd_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
	@Query(value="SELECT sheet.ipdno, sheet.uhid, sheet.date, sheet.organization, sheet.type, sheet.patho_flag, sheet.remarks, details.test_name AS testName, details.id AS requestid, details.time AS TIME FROM request_sheet_ipd sheet, request_sheet_ipd_details details WHERE sheet.id = details.request_id AND sheet.ipdno=:ipdno AND sheet.credit_year=:creditYear ;", nativeQuery = true)
	List<DoneTestOfPatient> getDoneTestOfPatient(@Param("creditYear") Long creditYear, @Param("ipdno") String ipdno);
	
}

interface DoneTestOfPatient{
	
	 String getIpdno();
	 String getUhid();
	 String getDate();
	 String getOrganization();
	 String getType();
	 String getPathoFlag();
	 String getRemarks();
	
	 String getRequestid();
	 String getTestName();
	 String getTime();
}
