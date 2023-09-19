package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_SampleDeviceMaster_Entity;

public interface Patho_SampleDeviceMaster_Repository extends JpaRepository<Patho_SampleDeviceMaster_Entity, Integer> {
	
	Patho_SampleDeviceMaster_Entity findBySampleName(String sampleName);
	
	Patho_SampleDeviceMaster_Entity findById(Long id);

}
