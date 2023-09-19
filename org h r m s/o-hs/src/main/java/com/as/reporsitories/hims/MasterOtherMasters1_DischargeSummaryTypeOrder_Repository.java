package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DischargeSummaryTypeOrder_Entity;

public interface MasterOtherMasters1_DischargeSummaryTypeOrder_Repository extends JpaRepository<MasterOtherMasters1_DischargeSummaryTypeOrder_Entity, Integer> {

	MasterOtherMasters1_DischargeSummaryTypeOrder_Entity findById(Long id);
	
}
