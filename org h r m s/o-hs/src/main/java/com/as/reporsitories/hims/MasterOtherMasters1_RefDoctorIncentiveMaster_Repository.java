package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Entity;

public interface MasterOtherMasters1_RefDoctorIncentiveMaster_Repository extends JpaRepository<MasterOtherMasters1_RefDoctorIncentiveMaster_Entity, Integer> {

	MasterOtherMasters1_RefDoctorIncentiveMaster_Entity findById(Long id);

}
