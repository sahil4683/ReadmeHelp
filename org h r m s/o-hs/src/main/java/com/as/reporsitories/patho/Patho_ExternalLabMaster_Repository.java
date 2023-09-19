package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_ExternalLabMaster_Entity;

public interface Patho_ExternalLabMaster_Repository extends JpaRepository<Patho_ExternalLabMaster_Entity, Integer> {

	Patho_ExternalLabMaster_Entity findByLabName(String labName);
	
	Patho_ExternalLabMaster_Entity findById(Long id);
}
