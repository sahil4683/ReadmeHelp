package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.ReceptionBillLab_Details_Entity;

public interface ReceptionBillLab_Details_Repository extends JpaRepository<ReceptionBillLab_Details_Entity, Integer> {
	
	ReceptionBillLab_Details_Entity findById(Long id);
	
	List<ReceptionBillLab_Details_Entity> findByBillNoAndCreditYear(String billNo,Long creditYear);

}
