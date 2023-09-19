package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_ExternalLabTestMaster_Entity;

public interface Patho_ExternalLabTestMaster_Repository extends JpaRepository<Patho_ExternalLabTestMaster_Entity, Integer> {

	Patho_ExternalLabTestMaster_Entity findById(Long id);
	
	Patho_ExternalLabTestMaster_Entity findByGroupNameAndTestNameAndLabName(String groupName, String testName, String labName);
	
}
