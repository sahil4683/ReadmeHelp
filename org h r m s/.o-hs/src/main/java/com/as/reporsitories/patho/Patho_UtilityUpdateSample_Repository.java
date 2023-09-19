package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_UtilityUpdateSample_Entity;

public interface Patho_UtilityUpdateSample_Repository extends JpaRepository<Patho_UtilityUpdateSample_Entity, Integer> {

	Patho_UtilityUpdateSample_Entity findById(Long id);
}
