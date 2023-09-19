package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_DoctorIncentiveMaster_Entity;

public interface MasterOtherMasters1_DoctorIncentiveMaster_Repository extends JpaRepository<MasterOtherMasters1_DoctorIncentiveMaster_Entity, Integer> {
		
	MasterOtherMasters1_DoctorIncentiveMaster_Entity findById(Long id);
	
	MasterOtherMasters1_DoctorIncentiveMaster_Entity findByDoctorName(String  doctorName);
	MasterOtherMasters1_DoctorIncentiveMaster_Entity findByDoctorNameView(String  doctorName);
	
}
