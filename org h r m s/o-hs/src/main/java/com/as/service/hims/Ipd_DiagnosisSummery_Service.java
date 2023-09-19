package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.Ipd_DiagnosisSummery_DTO;
import com.as.h_entities.hims.Ipd_DiagnosisSummery_Entity;
import com.as.reporsitories.hims.Ipd_DiagnosisSummery_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class Ipd_DiagnosisSummery_Service {

	@Autowired
	Ipd_DiagnosisSummery_Repository repository;
	
	public BaseResponse save(Ipd_DiagnosisSummery_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Ipd_DiagnosisSummery_Entity entity = repository.findByDiagnosis(form.getDiagnosis());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new Ipd_DiagnosisSummery_Entity();
		
		BeanUtils.copyProperties(form, entity);

		
		/*Other Modification On Entity
		 * */
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(Ipd_DiagnosisSummery_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Ipd_DiagnosisSummery_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
		return response;
	}

	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Ipd_DiagnosisSummery_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findDistinctAll());
		return response;
	}

	public BaseResponse getByChapter(String chapter, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByChapter(chapter));
		return response;
	}
	
}