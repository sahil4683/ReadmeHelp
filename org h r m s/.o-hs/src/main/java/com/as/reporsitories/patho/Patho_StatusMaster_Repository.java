package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_StatusMaster_Entity;

public interface Patho_StatusMaster_Repository extends JpaRepository<Patho_StatusMaster_Entity, Integer> {

	Patho_StatusMaster_Entity findByStatusName(String statusName);
	
	Patho_StatusMaster_Entity findById(Long id);
}
