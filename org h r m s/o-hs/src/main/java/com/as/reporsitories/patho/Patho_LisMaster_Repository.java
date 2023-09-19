package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_LisMaster_Entity;

public interface Patho_LisMaster_Repository extends JpaRepository<Patho_LisMaster_Entity, Integer> {

	Patho_LisMaster_Entity findByMachineTestIdAndLabTest(String machineTestId,String labTest);
	
	Patho_LisMaster_Entity findById(Long id);
	
}
