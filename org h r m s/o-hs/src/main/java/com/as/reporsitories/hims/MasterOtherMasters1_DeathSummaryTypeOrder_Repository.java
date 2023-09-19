package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DeathSummaryTypeOrder_Entity;

public interface MasterOtherMasters1_DeathSummaryTypeOrder_Repository extends JpaRepository<MasterOtherMasters1_DeathSummaryTypeOrder_Entity, Integer> {

	MasterOtherMasters1_DeathSummaryTypeOrder_Entity findById(Long id);
	
}
