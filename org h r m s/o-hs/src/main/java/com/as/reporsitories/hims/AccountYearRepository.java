package com.as.reporsitories.hims;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.entities.AccountYear_Entity;

public interface AccountYearRepository extends JpaRepository<AccountYear_Entity, Integer> {
	AccountYear_Entity findByAcYear(String acYear);
	AccountYear_Entity findById(int id);
	
	@Query("SELECT ACC FROM AccountYear_Entity ACC WHERE ACC.fromDate <= :date AND ACC.toDate >=:date")
	AccountYear_Entity findByFromDateGreaterThanEqualAndToDateLessThanEqual(@Param("date") String date);
	
}
