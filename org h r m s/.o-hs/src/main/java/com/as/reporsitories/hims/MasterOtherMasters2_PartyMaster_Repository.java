package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_PartyMaster_Entity;

public interface MasterOtherMasters2_PartyMaster_Repository extends JpaRepository<MasterOtherMasters2_PartyMaster_Entity, Integer> {

	MasterOtherMasters2_PartyMaster_Entity findById(Long id);
	
	MasterOtherMasters2_PartyMaster_Entity findByAccountName(String accountName);
	
	
}
