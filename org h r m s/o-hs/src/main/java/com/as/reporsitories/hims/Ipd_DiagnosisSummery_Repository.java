package com.as.reporsitories.hims;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.as.h_entities.hims.Ipd_DiagnosisSummery_Entity;
import com.as.response.DiagnosisResponse;

public interface Ipd_DiagnosisSummery_Repository extends JpaRepository<Ipd_DiagnosisSummery_Entity, Integer> {

	Ipd_DiagnosisSummery_Entity findByDiagnosis(String diagnosis);

	Ipd_DiagnosisSummery_Entity findById(Long id);
	
	@Query(value="SELECT DISTINCT group_name as groupName,chapter FROM ipd_diagnosis_master ORDER BY group_name;", nativeQuery = true)
	List<DiagnosisResponse> findDistinctAll();

	List<Ipd_DiagnosisSummery_Entity> findByChapter(String chapter);

}

