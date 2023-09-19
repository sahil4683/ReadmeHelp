package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_SubGroup1_Entity;

public interface MasterOtherMasters1_SubGroup1_Repository extends JpaRepository<MasterOtherMasters1_SubGroup1_Entity, Integer> {
	
	MasterOtherMasters1_SubGroup1_Entity findBySubGroup1(String subGroup1);
	
	MasterOtherMasters1_SubGroup1_Entity findById(Long id);
	
}
