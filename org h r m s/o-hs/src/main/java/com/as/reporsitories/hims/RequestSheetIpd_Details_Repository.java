package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.as.h_entities.hims.RequestSheetIpd_Details_Entity;

public interface RequestSheetIpd_Details_Repository extends JpaRepository<RequestSheetIpd_Details_Entity, Integer> {

	RequestSheetIpd_Details_Entity findById(Long id);

	List<RequestSheetIpd_Details_Entity> findByRequestIdAndCreditYear(String requestId, Long creditYear);

	@Query("select e from RequestSheetIpd_Details_Entity e where e.requestId in :ids")
	List<RequestSheetIpd_Details_Entity> findByRequestIds(@Param("ids") List<String> postIdsList);

}
