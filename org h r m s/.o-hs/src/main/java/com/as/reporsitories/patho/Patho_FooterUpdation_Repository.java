package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_FooterUpdation_Entity;

public interface Patho_FooterUpdation_Repository extends JpaRepository<Patho_FooterUpdation_Entity, Integer> {
	
	Patho_FooterUpdation_Entity findById(Long id);

}
