package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterTestMaster_IpdTest_Entity;

public interface MasterTestMaster_IpdTest_Repository extends JpaRepository<MasterTestMaster_IpdTest_Entity, Integer> {

	MasterTestMaster_IpdTest_Entity findById(Long id);
	
	MasterTestMaster_IpdTest_Entity findByTestName(String testName);
	
	List<MasterTestMaster_IpdTest_Entity> findByGroupNameAndOrganizationName(String groupName,String organizationName);
	
	List<MasterTestMaster_IpdTest_Entity> findByOrganizationName(String organizationName);
	
	List<MasterTestMaster_IpdTest_Entity> findByDailyYn(String dailyYn);
	
}
