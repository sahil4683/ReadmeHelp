package com.as.reporsitories.patho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.as.h_entities.patho.Patho_FormatGroupMaster_Entity;

public interface Patho_FormatGroupMaster_Repository extends JpaRepository<Patho_FormatGroupMaster_Entity, Integer> {

	Patho_FormatGroupMaster_Entity findById(Long Id);
	
	List<Patho_FormatGroupMaster_Entity> findBySno(String sno);
	
	List<Patho_FormatGroupMaster_Entity> findByFormatTestId(String formatTestId);
	
	List<Patho_FormatGroupMaster_Entity> findByFormatTest(String formatTest);
	
	@Query("SELECT MAX(cast(a.sno as int)) FROM Patho_FormatGroupMaster_Entity a")
	Long getLastSno();
	
}
