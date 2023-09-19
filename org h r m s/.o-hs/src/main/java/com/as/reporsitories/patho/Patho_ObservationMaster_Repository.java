package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_ObservationMaster_Entity;

public interface Patho_ObservationMaster_Repository extends JpaRepository<Patho_ObservationMaster_Entity, Integer> {

	Patho_ObservationMaster_Entity findByTestName(String testName);
	
	Patho_ObservationMaster_Entity findById(Long id);
}
