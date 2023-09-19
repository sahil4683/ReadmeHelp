package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DeathSummaryType_Entity;

public interface MasterOtherMasters1_DeathSummaryType_Repository extends JpaRepository<MasterOtherMasters1_DeathSummaryType_Entity, Integer> {

	MasterOtherMasters1_DeathSummaryType_Entity findByType(String type);
	
	MasterOtherMasters1_DeathSummaryType_Entity findById(Long id);
	
}
