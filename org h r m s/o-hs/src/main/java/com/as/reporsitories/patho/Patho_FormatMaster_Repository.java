package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_FormatMaster_Entity;

public interface Patho_FormatMaster_Repository extends JpaRepository<Patho_FormatMaster_Entity, Integer> {

	Patho_FormatMaster_Entity findByFormatName(String formatName);
	
	Patho_FormatMaster_Entity findById(Long id);
}
