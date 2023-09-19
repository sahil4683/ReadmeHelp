package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters2_PlasticMoneyMaster_Entity;

public interface MasterOtherMasters2_PlasticMoneyMaster_Repository extends JpaRepository<MasterOtherMasters2_PlasticMoneyMaster_Entity, Integer> {
	
	MasterOtherMasters2_PlasticMoneyMaster_Entity findByPlasticInstrumentName(String plasticInstrumentName);

	MasterOtherMasters2_PlasticMoneyMaster_Entity findById(Long id);
	
}
