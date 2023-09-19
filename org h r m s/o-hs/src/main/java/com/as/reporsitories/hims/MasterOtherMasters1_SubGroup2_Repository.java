package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_SubGroup2_Entity;

public interface MasterOtherMasters1_SubGroup2_Repository extends JpaRepository<MasterOtherMasters1_SubGroup2_Entity, Integer> {
	
	MasterOtherMasters1_SubGroup2_Entity findBySubGroup2(String subGroup2);
	
	MasterOtherMasters1_SubGroup2_Entity findById(Long id);
	
}
