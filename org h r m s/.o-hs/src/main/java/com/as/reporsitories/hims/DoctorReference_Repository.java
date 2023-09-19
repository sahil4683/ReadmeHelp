package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.DoctorReference_Entity;

public interface DoctorReference_Repository extends JpaRepository<DoctorReference_Entity, Integer> {

	DoctorReference_Entity findById(Long id);
	
	DoctorReference_Entity findByName(String name);
}
