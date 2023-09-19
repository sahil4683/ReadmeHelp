package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_TpaMaster_Entity;

public interface MasterOtherMasters2_TpaMaster_Repository extends JpaRepository<MasterOtherMasters2_TpaMaster_Entity, Integer> {

	MasterOtherMasters2_TpaMaster_Entity findById(Long id);
	
	MasterOtherMasters2_TpaMaster_Entity findByTpaName(String tpaName);
	
}
