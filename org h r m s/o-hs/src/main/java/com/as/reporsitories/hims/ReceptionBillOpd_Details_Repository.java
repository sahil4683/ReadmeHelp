package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.ReceptionBillOpd_Details_Entity;

public interface ReceptionBillOpd_Details_Repository extends JpaRepository<ReceptionBillOpd_Details_Entity, Integer> {
	
	ReceptionBillOpd_Details_Entity findById(Long id);
	
	List<ReceptionBillOpd_Details_Entity> findByBillNoAndCreditYear(String billNo,Long creditYear);

}
