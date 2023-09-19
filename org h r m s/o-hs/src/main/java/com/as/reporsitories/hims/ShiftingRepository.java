package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.Shifting;

public interface ShiftingRepository extends JpaRepository<Shifting, Integer> {
	
	Shifting findById(Long id);
	
	List<Shifting> findByCreditYearOrderByIdDesc(Long creditYear);
	
	List<Shifting> findByShiftDateAndCreditYear(String date,Long creditYear);
	
}
