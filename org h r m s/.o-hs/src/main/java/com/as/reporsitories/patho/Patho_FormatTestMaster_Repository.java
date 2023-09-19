package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_FormatTestMaster_Entity;

public interface Patho_FormatTestMaster_Repository extends JpaRepository<Patho_FormatTestMaster_Entity, Integer> {

	Patho_FormatTestMaster_Entity findByFormatTestName(String formatTestName);
	Patho_FormatTestMaster_Entity findById(Long id);
}
