package com.as.reporsitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.entities.NumberSequenceUtility;

public interface NumberSequenceUtilityRepository extends JpaRepository<NumberSequenceUtility, Integer> {

	NumberSequenceUtility findByName(String name);
	
}
