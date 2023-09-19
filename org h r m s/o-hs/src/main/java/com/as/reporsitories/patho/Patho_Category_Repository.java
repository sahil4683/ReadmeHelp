package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_Category_Entity;

public interface Patho_Category_Repository extends JpaRepository<Patho_Category_Entity, Integer> {
	
	Patho_Category_Entity findByCategoryName(String categoryName);

	Patho_Category_Entity findById(long id);

}
