package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_Concession_Entity;

public interface MasterOtherMasters1_Concession_Repository extends JpaRepository<MasterOtherMasters1_Concession_Entity, Integer> {

	MasterOtherMasters1_Concession_Entity findById(Long id);
	
	MasterOtherMasters1_Concession_Entity findByConcessionName(String concessionName);
}
