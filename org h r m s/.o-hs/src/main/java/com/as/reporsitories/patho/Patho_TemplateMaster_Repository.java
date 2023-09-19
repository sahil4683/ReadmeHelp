package com.as.reporsitories.patho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.as.h_entities.patho.Patho_TemplateMaster_Entity;

public interface Patho_TemplateMaster_Repository extends JpaRepository<Patho_TemplateMaster_Entity, Integer> {

	Patho_TemplateMaster_Entity findById(Long Id);
	
	List<Patho_TemplateMaster_Entity> findBySno(String sno);
	
//	List<Patho_TemplateMaster_Entity> findByFormatTestId(String formatTestId);
	
	@Query("SELECT MAX(cast(a.sno as int)) FROM Patho_TemplateMaster_Entity a")
	Long getLastSno();
	
}
