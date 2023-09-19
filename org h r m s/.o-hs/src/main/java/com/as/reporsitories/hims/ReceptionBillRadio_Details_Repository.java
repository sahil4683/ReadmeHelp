package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.ReceptionBillRadio_Details_Entity;

public interface ReceptionBillRadio_Details_Repository extends JpaRepository<ReceptionBillRadio_Details_Entity, Integer> {
	
	ReceptionBillRadio_Details_Entity findById(Long id);
	
	List<ReceptionBillRadio_Details_Entity> findByBillNoAndCreditYear(String billNo,Long creditYear);

}
