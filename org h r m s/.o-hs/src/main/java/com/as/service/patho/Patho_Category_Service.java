package com.as.service.patho;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.h_dto.patho.Patho_Category_DTO;
import com.as.h_entities.patho.Patho_Category_Entity;
import com.as.reporsitories.patho.Patho_Category_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class Patho_Category_Service {

	@Autowired
	Patho_Category_Repository repository;
	
	//For Feature Development
	public BaseResponse save(Patho_Category_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_Category_Entity entity = repository.findByCategoryName(form.getCategoryName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new Patho_Category_Entity();
		BeanUtils.copyProperties(form, entity);
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	//For Feature Development
	public BaseResponse update(Patho_Category_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_Category_Entity entity = repository.findById(form.getId());
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

	//For Feature Development
	public BaseResponse delete(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_Category_Entity entity = repository.findById(id);
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
