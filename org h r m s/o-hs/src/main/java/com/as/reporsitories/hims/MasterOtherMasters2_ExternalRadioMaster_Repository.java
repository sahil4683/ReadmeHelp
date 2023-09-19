package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_ExternalRadioMaster_Entity;

public interface MasterOtherMasters2_ExternalRadioMaster_Repository extends JpaRepository<MasterOtherMasters2_ExternalRadioMaster_Entity, Integer> {

	MasterOtherMasters2_ExternalRadioMaster_Entity findById(Long id);
	
	MasterOtherMasters2_ExternalRadioMaster_Entity findByRadioName(String radioName);
}
