package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.Master_Category_Entity;

public interface Master_Category_Repository extends JpaRepository<Master_Category_Entity, Integer> {
	
	Master_Category_Entity findByCategoryTypeAndCategoryName(String categoryType,String categoryName);
	
	List<Master_Category_Entity> findByCategoryType(String categoryType);

	Master_Category_Entity findById(long id);

}
