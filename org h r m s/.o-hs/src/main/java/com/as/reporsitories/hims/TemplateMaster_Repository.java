package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.TemplateMaster_Entity;

public interface TemplateMaster_Repository extends JpaRepository<TemplateMaster_Entity, Integer> {

	TemplateMaster_Entity findByTemplate(String template);

	TemplateMaster_Entity findById(Long id);
	
	List<TemplateMaster_Entity> findByTemplateType(String type);

}
