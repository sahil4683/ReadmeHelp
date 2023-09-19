package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.DischargeSummary_Entity;

public interface DischargeSummary_Repository extends JpaRepository<DischargeSummary_Entity, Integer> {

	DischargeSummary_Entity findById(Long id);
	
	List<DischargeSummary_Entity> findByCreditYearOrderByIdDesc(Long creditYear);
	List<DischargeSummary_Entity> findByProcedureDateAndCreditYearOrderByIdDesc(String Date, Long creditYear);
	
	DischargeSummary_Entity findByIpdnoAndUhidAndCreditYear(String ipdno,String uhid, Long creditYear);
	
}
