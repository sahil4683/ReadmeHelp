package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.ReceptionBillOpdHealthCheckup_Details_Entity;

public interface ReceptionBillOpdHealthCheckup_Details_Repository extends JpaRepository<ReceptionBillOpdHealthCheckup_Details_Entity, Integer> {
	
	ReceptionBillOpdHealthCheckup_Details_Entity findById(Long id);
	
	List<ReceptionBillOpdHealthCheckup_Details_Entity> findByBillNoAndCreditYear(String billNo,Long creditYear);

}
