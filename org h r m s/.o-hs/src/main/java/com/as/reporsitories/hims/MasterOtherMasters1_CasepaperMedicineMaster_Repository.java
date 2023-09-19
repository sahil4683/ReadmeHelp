package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_CasepaperMedicineMaster_Entity;

public interface MasterOtherMasters1_CasepaperMedicineMaster_Repository extends JpaRepository<MasterOtherMasters1_CasepaperMedicineMaster_Entity, Integer> {

	MasterOtherMasters1_CasepaperMedicineMaster_Entity findById(Long id);
	
	MasterOtherMasters1_CasepaperMedicineMaster_Entity findByMedicine(String medicine);
	
}
