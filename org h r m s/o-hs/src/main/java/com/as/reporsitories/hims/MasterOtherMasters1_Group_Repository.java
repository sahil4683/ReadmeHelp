package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_Group_Entity;

public interface MasterOtherMasters1_Group_Repository extends JpaRepository<MasterOtherMasters1_Group_Entity, Integer> {


	MasterOtherMasters1_Group_Entity findByGroupName(String group);

	MasterOtherMasters1_Group_Entity findById(Long id);
	
	List<MasterOtherMasters1_Group_Entity> findByDepartment(String department);

	List<MasterOtherMasters1_Group_Entity> findByDepartmentAndSuperGroup(String department, String superGroup);
	
}
