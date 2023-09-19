package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterPackageMaster_IpdPackageMaster_Details_Entity;

public interface MasterPackageMaster_IpdPackageMaster_Details_Repository extends JpaRepository<MasterPackageMaster_IpdPackageMaster_Details_Entity, Integer> {
	
	MasterPackageMaster_IpdPackageMaster_Details_Entity findById(Long id);
	
	List<MasterPackageMaster_IpdPackageMaster_Details_Entity> findByPackageId(String packageId);
}
