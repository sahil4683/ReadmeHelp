package com.as.reporsitories.patho;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.patho.Patho_AuditTrailPatho_Entity;

public interface Patho_AuditTrailPatho_Repository extends JpaRepository<Patho_AuditTrailPatho_Entity, Integer> {
	
	Patho_AuditTrailPatho_Entity findById(Long id);
	
	Patho_AuditTrailPatho_Entity findByrecordId(String recordId);
	
}
