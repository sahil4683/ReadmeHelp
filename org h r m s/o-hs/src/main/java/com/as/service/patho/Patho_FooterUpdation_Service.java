package com.as.service.patho;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.patho.Patho_FooterUpdation_DTO;
import com.as.h_entities.patho.Patho_FooterUpdation_Entity;
import com.as.reporsitories.patho.Patho_FooterUpdation_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class Patho_FooterUpdation_Service {

	@Autowired
	Patho_FooterUpdation_Repository repository;

	public BaseResponse update(Patho_FooterUpdation_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_FooterUpdation_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			form.setId(1l); // Temporary Solution !
			entity = new Patho_FooterUpdation_Entity();
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
