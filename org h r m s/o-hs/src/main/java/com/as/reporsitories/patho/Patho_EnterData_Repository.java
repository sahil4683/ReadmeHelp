package com.as.reporsitories.patho;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_EnterData_Entity;

public interface Patho_EnterData_Repository extends JpaRepository<Patho_EnterData_Entity, Integer> {

	List<Patho_EnterData_Entity> findBySnoAndCreditYear(String sno, Long creditYear);
	
	Patho_EnterData_Entity findById(Long id);
	
}
