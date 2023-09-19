package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_MapLabTest_Entity;

public interface Patho_MapLabTest_Repository extends JpaRepository<Patho_MapLabTest_Entity, Integer> {

	Patho_MapLabTest_Entity findByOldTest(String oldTest);
}
