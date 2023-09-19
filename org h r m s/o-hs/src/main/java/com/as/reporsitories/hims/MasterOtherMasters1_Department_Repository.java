package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_Department_Entity;

public interface MasterOtherMasters1_Department_Repository extends JpaRepository<MasterOtherMasters1_Department_Entity, Integer> {
	
	MasterOtherMasters1_Department_Entity findByDept(String dept);

	MasterOtherMasters1_Department_Entity findById(Long id);

}
