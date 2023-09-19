package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_TDSMaster_Entity;

public interface MasterOtherMasters1_TDSMaster_Repository extends JpaRepository<MasterOtherMasters1_TDSMaster_Entity, Integer> {
	
	MasterOtherMasters1_TDSMaster_Entity findByCategoryAndStatus(String category,String status);
	
	MasterOtherMasters1_TDSMaster_Entity findById(Long id);
	
}
