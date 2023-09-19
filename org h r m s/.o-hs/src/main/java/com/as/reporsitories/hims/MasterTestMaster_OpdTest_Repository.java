package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterTestMaster_OpdTest_Entity;

public interface MasterTestMaster_OpdTest_Repository extends JpaRepository<MasterTestMaster_OpdTest_Entity, Integer> {

	MasterTestMaster_OpdTest_Entity findById(Long id);
	
	MasterTestMaster_OpdTest_Entity findByTestName(String testName);
	
	List<MasterTestMaster_OpdTest_Entity> findByGroupName(String groupName);
	
	List<MasterTestMaster_OpdTest_Entity> findByOrganizationName(String organizationName);
	
	List<MasterTestMaster_OpdTest_Entity> findByGroupNameAndOrganizationName(String groupName,String organizationName);
	
}
