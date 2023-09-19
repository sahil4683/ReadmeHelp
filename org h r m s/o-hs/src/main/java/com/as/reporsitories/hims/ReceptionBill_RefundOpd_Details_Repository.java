package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.ReceptionBill_RefundOpd_Details_Entity;

public interface ReceptionBill_RefundOpd_Details_Repository extends JpaRepository<ReceptionBill_RefundOpd_Details_Entity, Integer> {
	
	ReceptionBill_RefundOpd_Details_Entity findById(Long id);
	
	List<ReceptionBill_RefundOpd_Details_Entity> findByBillNoAndCreditYear(String billNo,Long creditYear);

}
