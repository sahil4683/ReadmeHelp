package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity;

public interface MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Repository extends JpaRepository<MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity, Integer> {

	MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity findById(Long id);

	List<MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity> findByIncMasterId(String valueOf);

}
