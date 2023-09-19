package com.as.service.patho;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.h_dto.patho.Patho_TemplateMaster_DTO;
import com.as.h_entities.patho.Patho_TemplateMaster_Entity;
import com.as.reporsitories.patho.Patho_TemplateMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class Patho_TemplateMaster_Service {

	@Autowired
	Patho_TemplateMaster_Repository repository;
	
	public BaseResponse save(List<Patho_TemplateMaster_DTO> forms, Principal principal) {
		BaseResponse response = new BaseResponse();

		List<Patho_TemplateMaster_Entity> entityList=new ArrayList<>();
		for(Patho_TemplateMaster_DTO form: forms) {
			Patho_TemplateMaster_Entity entity = new Patho_TemplateMaster_Entity();
			BeanUtils.copyProperties(form, entity);
			
			Long LastId = repository.getLastSno();
			LastId = (LastId == null ? 1 : LastId + 1);
			entity.setSno(String.valueOf(LastId));
			
			entityList.add(entity);
		}
		
		entityList = repository.saveAll(entityList);

		if (entityList.size() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(List<Patho_TemplateMaster_DTO> forms, Principal principal) {
		BaseResponse response = new BaseResponse();
		List<Patho_TemplateMaster_Entity> entityList = new ArrayList<>();
		for(Patho_TemplateMaster_DTO  form : forms) {
			Patho_TemplateMaster_Entity entity = repository.findById(form.getId());
			if(entity == null) {
				entity = new Patho_TemplateMaster_Entity();
			}
			BeanUtils.copyProperties(form, entity);
			entityList.add(entity);
		}
		entityList =repository.saveAll(entityList);
		if (entityList.size() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.RESPONSE_ERROR);
		response.setMessage("Error Not Update !");
		return response;
	}

	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_TemplateMaster_Entity entity = repository.findById(id);
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
		response.setBody(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		return response;
	}
	
}
