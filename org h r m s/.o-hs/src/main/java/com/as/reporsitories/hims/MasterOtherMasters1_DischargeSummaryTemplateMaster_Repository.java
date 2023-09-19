package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DischargeSummaryTemplateMaster_Entity;

public interface MasterOtherMasters1_DischargeSummaryTemplateMaster_Repository extends JpaRepository<MasterOtherMasters1_DischargeSummaryTemplateMaster_Entity, Integer>{
	
	MasterOtherMasters1_DischargeSummaryTemplateMaster_Entity findById(Long id);
	
	MasterOtherMasters1_DischargeSummaryTemplateMaster_Entity findByTemplate(String template);
}
