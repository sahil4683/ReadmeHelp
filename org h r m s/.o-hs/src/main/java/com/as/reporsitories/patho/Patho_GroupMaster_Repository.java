package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_GroupMaster_Entity;

public interface Patho_GroupMaster_Repository extends JpaRepository<Patho_GroupMaster_Entity, Integer> {

	Patho_GroupMaster_Entity findByGroupName(String groupName);
	
	Patho_GroupMaster_Entity findById(Long id);
}
