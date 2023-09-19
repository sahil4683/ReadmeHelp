package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.DeathSummary_Entity;

public interface DeathSummary_Repository extends JpaRepository<DeathSummary_Entity, Integer> {

	DeathSummary_Entity findById(Long id);
	
	List<DeathSummary_Entity> findByProcedureDateAndCreditYearOrderByIdDesc(String Date,Long creditYear);
	
	DeathSummary_Entity findByIpdnoAndUhidAndCreditYear(String ipdno,String uhid,Long creditYear);
	
	List<DeathSummary_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	
}
