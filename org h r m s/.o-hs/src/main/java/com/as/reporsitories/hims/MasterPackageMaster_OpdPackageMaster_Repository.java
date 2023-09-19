package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterPackageMaster_OpdPackageMaster_Entity;

public interface MasterPackageMaster_OpdPackageMaster_Repository extends JpaRepository<MasterPackageMaster_OpdPackageMaster_Entity, Integer> {
	
	MasterPackageMaster_OpdPackageMaster_Entity findByPackageName(String packageName);
	
	MasterPackageMaster_OpdPackageMaster_Entity findById(Long id);
	
}
