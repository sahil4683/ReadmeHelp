package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.IpdBill_ProvisionalBill_Entity;
import com.as.response.BillNameBillNoUhid;

public interface IpdBill_ProvisionalBill_Repository extends JpaRepository<IpdBill_ProvisionalBill_Entity, Integer> {

	IpdBill_ProvisionalBill_Entity findByIpdnoAndCreditYear(String ipdno, Long creditYear);

	IpdBill_ProvisionalBill_Entity findById(Long id);

	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_ProvisionalBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.creditYear=:creditYear")
	List<Object[]> findByAllTable(@Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_ProvisionalBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.date=:date AND a.creditYear=:creditYear ")
	List<Object[]> findByAllTableByDate(@Param("date") String date,@Param("creditYear") Long creditYear);
	
	
	
	@Query(value="SELECT "
			+ " bill.id AS id, "
			+ " reg.name AS name, "
			+ " bill.bill_no AS billNo, "
			+ " bill.uhid AS uhid, "
			+ " bill.ipdno AS ipd, "
			+ " bill.date AS date "
			+ " FROM ipd_provisional_bill bill "
			+ " JOIN reception_reception_registration reg "
			+ " ON  bill.uhid = reg.uhid "
			+ " WHERE bill.credit_year=:creditYear AND "
			+ " reg.name LIKE :searchtext% OR "
			+ "	bill.bill_no LIKE :searchtext% OR "
			+ "	bill.ipdno LIKE :searchtext% OR "
			+ "	bill.uhid LIKE :searchtext%  "
			+ " ORDER BY bill.id DESC LIMIT 50;", nativeQuery = true)
	List<BillNameBillNoUhid> searchOnTableData(@Param("searchtext") String searchtext, @Param("creditYear") Long creditYear);
	
	@Query(value="SELECT a,r.Name as name FROM ReceptionReceptionRegistration_Entity r RIGHT JOIN IpdBill_ProvisionalBill_Entity a ON a.uhid = r.UHID WHERE r.UHID = a.uhid AND a.id=:id AND a.creditYear=:creditYear ")
	List<Object[]> findByAllTableById(@Param("id") Long id,@Param("creditYear") Long creditYear);
	
	
	
}
