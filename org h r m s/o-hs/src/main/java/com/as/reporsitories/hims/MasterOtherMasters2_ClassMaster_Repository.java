package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_ClassMaster_Entity;

public interface MasterOtherMasters2_ClassMaster_Repository extends JpaRepository<MasterOtherMasters2_ClassMaster_Entity, Integer> {

	MasterOtherMasters2_ClassMaster_Entity findByClassName(String className);
	
	MasterOtherMasters2_ClassMaster_Entity findById(Long id);
}
