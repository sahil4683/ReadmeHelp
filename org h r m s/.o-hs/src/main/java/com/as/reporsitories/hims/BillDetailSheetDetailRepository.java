package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.as.h_entities.hims.BillDetailSheetDetail;

public interface BillDetailSheetDetailRepository extends JpaRepository<BillDetailSheetDetail, Integer> {

	BillDetailSheetDetail findById(Long id);
	
	List<BillDetailSheetDetail> findByBillNoAndCreditYear(String billNo, Long creditYear);
}
