package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterPackageMaster_IpdPackageMaster_Entity;

public interface MasterPackageMaster_IpdPackageMaster_Repository extends JpaRepository<MasterPackageMaster_IpdPackageMaster_Entity, Integer> {
	
	MasterPackageMaster_IpdPackageMaster_Entity findByPackageName(String packageName);
	
	MasterPackageMaster_IpdPackageMaster_Entity findById(Long id);
	
}
