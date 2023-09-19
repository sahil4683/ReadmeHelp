package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.as.h_entities.hims.BedMaster;
import com.as.response.BedResponseInterface;

public interface BedMasterRepository extends JpaRepository<BedMaster, Integer> {
	
	List<BedMaster> findBybookedynOrderByBedno(int num);
	
	BedMaster findByBedno(String bedNumber);
	
	BedMaster findById(Long id);
	
	@Query(value = "SELECT " + 
			"b.bedno AS bedNumber, " + 
			"c.name AS consultantName, " + 
			"r.name AS patientName, " + 
			"a.ipdno AS ipdno, " + 
			"r.uhid AS uhid, " + 
			"a.typeof_patient AS patientType, " + 
			"IF(a.discharge=\"NO\", 1, 0) AS occupied, " + 
			"null AS sequence " + 
			"FROM bedmaster b, reception_reception_admission a , reception_reception_registration r, consultant_master c " + 
			"WHERE a.bedno = b.bedno AND r.uhid = a.uhid AND a.consultant1= c.id AND discharge =\"NO\" " + 
			"UNION " + 
			"SELECT " + 
			"b.bedno AS bedNumber, " + 
			"null AS consultantName, " + 
			"null AS patientName, " + 
			"null AS ipdno, " + 
			"null AS uhid, " + 
			"null AS patientType, " + 
			"IF(b.bookedyn=\"1\", 1, 0) AS occupied, " + 
			"b.sequence AS sequence " + 
			"FROM bedmaster b WHERE b.bookedyn=\"0\" " + 
			"ORDER BY sequence",
			nativeQuery = true)
	List<BedResponseInterface> getBedList();
	
}
