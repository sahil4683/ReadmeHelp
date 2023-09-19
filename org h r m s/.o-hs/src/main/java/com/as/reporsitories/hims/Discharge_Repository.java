package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.Discharge_Entity;

public interface Discharge_Repository extends JpaRepository<Discharge_Entity, Long> {

//	List<Discharge_Entity> findByDate(String date);
	List<Discharge_Entity> findByDateAndCreditYearOrderByIdDesc(String date,Long creditYear);

	List<Discharge_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
}
