package com.as.reporsitories.hims;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.MasterAppointmentMaster_AppointmentScheduler_Entity;

public interface MasterAppointmentMaster_AppointmentScheduler_Repository extends JpaRepository<MasterAppointmentMaster_AppointmentScheduler_Entity, Integer>{
 
	MasterAppointmentMaster_AppointmentScheduler_Entity findById(Long id);
	
	MasterAppointmentMaster_AppointmentScheduler_Entity findByDoctorName(String doctorName);
	
}
