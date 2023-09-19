package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DischargeSummaryType_Entity;

public interface MasterOtherMasters1_DischargeSummaryType_Repository extends JpaRepository<MasterOtherMasters1_DischargeSummaryType_Entity, Integer> {

	MasterOtherMasters1_DischargeSummaryType_Entity findByType(String type);
	
	MasterOtherMasters1_DischargeSummaryType_Entity findById(Long id);
	
}
