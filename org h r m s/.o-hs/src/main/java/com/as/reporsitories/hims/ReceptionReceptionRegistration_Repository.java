package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.response.BillNameBillNoUhid;
import com.as.response.DashboardCount;

public interface ReceptionReceptionRegistration_Repository extends JpaRepository<ReceptionReceptionRegistration_Entity, Integer> {

	ReceptionReceptionRegistration_Entity findById(Long valueOf);
	
	@Query("SELECT a FROM ReceptionReceptionRegistration_Entity a WHERE a.Mobile=?1 AND a.Name=?2")
	ReceptionReceptionRegistration_Entity findByMobileAndName(String Mobile,String name);

//	@Query("SELECT MAX(a.UHID) FROM ReceptionReceptionRegistration_Entity a")
//	Long getNextUHID();
	
	@Query("SELECT MAX(cast(a.TOKENNO as int)) FROM ReceptionReceptionRegistration_Entity a WHERE a.date=?1")
	Long getNextTOKEN(String date);
	
	@Query("SELECT MAX(cast(a.mlcno as int)) FROM ReceptionReceptionRegistration_Entity a")
	Long getNextMLC();
	
	ReceptionReceptionRegistration_Entity findByUHID(String uHID);

	List<ReceptionReceptionRegistration_Entity> findByDate(String date);
//	@Query(value="SELECT rg.*,con.name AS consultantName, org.organization AS organizationName FROM reception_reception_registration rg, consultant_master con, organization_master org WHERE rg.consultant=con.id AND rg.organization=org.id AND rg.date=:date ;", nativeQuery = true)
//	List<ReceptionReceptionRegistration_Table> findByDate(@Param("date") String date);

//	@Query(value="SELECT r.id,r.name as name,r.uhid as uhid FROM reception_reception_registration r WHERE LEFT (r.DATE, 4)=\"2021\" ORDER BY r.id DESC;", nativeQuery = true)
//	@Query(value="SELECT r.id,r.name as name,r.uhid as uhid FROM reception_reception_registration r ORDER BY r.id DESC;", nativeQuery = true)
//	List<BillNameBillNoUhid> getUhidName();
	
	@Query(value="SELECT r.id, r.name as name, r.uhid as uhid FROM reception_reception_registration r WHERE name LIKE :searchtext% OR uhid LIKE :searchtext% ORDER BY r.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> filterUhidName(@Param("searchtext") String searchtext);

	@Query(value="SELECT " + 
			"(Select COUNT(*) FROM reception_reception_registration r WHERE r.date=current_date() AND r.credit_year=:creditYear ) AS todayRegisterd, " + 
			"(Select COUNT(*) FROM reception_reception_admission a WHERE a.credit_year=:creditYear ) AS totalAdmited, " + 
			"(Select COUNT(*) FROM reception_reception_admission a WHERE a.date=current_date() AND a.credit_year=:creditYear ) AS todayAdmited, " + 
			"(Select COUNT(*) FROM reception_reception_admission a WHERE a.discharge=\"YES\" AND a.date=current_date() AND a.credit_year=:creditYear ) AS todayDischarge, " + 
			"(Select COUNT(*) FROM bedmaster b ) AS totalBeds, " + 
			"(Select COUNT(*) FROM bedmaster b WHERE b.bookedyn=\"0\") AS availableBeds, " + 
			"(Select COUNT(*) FROM bedmaster b WHERE b.bookedyn=\"1\") AS occupiedBeds" + 
			";", nativeQuery = true)
	DashboardCount getCount(@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT COUNT(*) FROM reception_reception_registration r ;", nativeQuery = true)
	String getRegistredCount(@Param("creditYear") Long creditYear);
	
	
	@Query(value="SELECT "
			+ " reg.id AS id, "
			+ " reg.name AS name, "
			+ " reg.uhid AS uhid, "
			+ " reg.date AS date "
			+ " FROM reception_reception_registration reg "
			+ " WHERE "
			+ " reg.name LIKE :searchtext% OR  "
			+ "	reg.uhid LIKE :searchtext%  "
			+ " ORDER BY reg.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext);
	
}

//interface ReceptionReceptionRegistration_Table{
//	String getConsultantName();
//	String getOrganizationName();
//}

