package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.Discharge_Entity;

public interface IpdDischange_DischargeClearance_Repository extends JpaRepository<Discharge_Entity, Integer> {

	Discharge_Entity findById(Long id);
	
	List<Discharge_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
}
