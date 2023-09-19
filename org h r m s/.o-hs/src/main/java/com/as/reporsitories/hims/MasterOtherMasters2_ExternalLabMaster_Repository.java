package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_ExternalLabMaster_Entity;

public interface MasterOtherMasters2_ExternalLabMaster_Repository extends JpaRepository<MasterOtherMasters2_ExternalLabMaster_Entity, Integer> {
	
	MasterOtherMasters2_ExternalLabMaster_Entity findById(Long id);
	
	MasterOtherMasters2_ExternalLabMaster_Entity findByLabName(String labName);
	
}
