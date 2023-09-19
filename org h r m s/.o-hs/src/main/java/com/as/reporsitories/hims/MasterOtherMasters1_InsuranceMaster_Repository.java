package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_InsuranceMaster_Entity;

public interface MasterOtherMasters1_InsuranceMaster_Repository extends JpaRepository<MasterOtherMasters1_InsuranceMaster_Entity, Integer> {
	
	MasterOtherMasters1_InsuranceMaster_Entity findById(Long id);

	MasterOtherMasters1_InsuranceMaster_Entity findByInsuranceCompany(String insuranceCompany);
	
}
