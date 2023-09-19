package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_SuperGroup_Entity;

public interface MasterOtherMasters1_SuperGroup_Repository extends JpaRepository<MasterOtherMasters1_SuperGroup_Entity, Integer> {
	
	MasterOtherMasters1_SuperGroup_Entity findBySuperGroup(String superGroup);
	
	MasterOtherMasters1_SuperGroup_Entity findById(Long id);
	
}
