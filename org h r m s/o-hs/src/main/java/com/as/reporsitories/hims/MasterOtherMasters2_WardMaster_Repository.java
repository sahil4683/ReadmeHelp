package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_WardMaster_Entity;

public interface MasterOtherMasters2_WardMaster_Repository extends JpaRepository<MasterOtherMasters2_WardMaster_Entity, Integer> {

	MasterOtherMasters2_WardMaster_Entity findByWardName(String wardName);
	
	MasterOtherMasters2_WardMaster_Entity findById(Long id);
}
