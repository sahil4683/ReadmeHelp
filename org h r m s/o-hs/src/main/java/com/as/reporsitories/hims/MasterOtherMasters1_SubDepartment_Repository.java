package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_SubDepartment_Entity;

public interface MasterOtherMasters1_SubDepartment_Repository extends JpaRepository<MasterOtherMasters1_SubDepartment_Entity, Integer> {
	
	MasterOtherMasters1_SubDepartment_Entity findBySubDept(String subDept);

	MasterOtherMasters1_SubDepartment_Entity findById(Long id);
	
	List<MasterOtherMasters1_SubDepartment_Entity> findByDepartment(String department);
	

}
