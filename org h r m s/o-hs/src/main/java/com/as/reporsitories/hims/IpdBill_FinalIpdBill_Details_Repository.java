package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.IpdBill_FinalIpdBill_Details_Entity;

public interface IpdBill_FinalIpdBill_Details_Repository extends JpaRepository<IpdBill_FinalIpdBill_Details_Entity, Integer> {

	IpdBill_FinalIpdBill_Details_Entity findById(Long id);
	
	List<IpdBill_FinalIpdBill_Details_Entity> findByIpdBillNoAndCreditYear(String ipdBillNo, Long creditYear);
}
