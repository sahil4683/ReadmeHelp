package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.OrganizationMaster_Entity;

public interface OrganizationMaster_Repository extends JpaRepository<OrganizationMaster_Entity, Integer> {

	OrganizationMaster_Entity findByOrganization(String organization);

	OrganizationMaster_Entity findById(Long id);
	
}
