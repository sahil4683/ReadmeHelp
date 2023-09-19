package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_SubGroup_Entity;

public interface MasterOtherMasters1_SubGroup_Repository extends JpaRepository<MasterOtherMasters1_SubGroup_Entity, Integer> {
	
	MasterOtherMasters1_SubGroup_Entity findBySubGroup(String subGroup);
	
	MasterOtherMasters1_SubGroup_Entity findById(Long id);
	
}
