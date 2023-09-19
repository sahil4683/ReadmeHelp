package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_MachineMaster_Entity;

public interface Patho_MachineMaster_Repository extends JpaRepository<Patho_MachineMaster_Entity, Integer> {
	
	Patho_MachineMaster_Entity findByMachineName(String machineName);
	
	Patho_MachineMaster_Entity findById(Long id);

}
