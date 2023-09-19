package com.as.service.patho;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.patho.Patho_StatusMaster_DTO;
import com.as.h_entities.patho.Patho_StatusMaster_Entity;
import com.as.reporsitories.patho.Patho_StatusMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class Patho_StatusMaster_Service {

	@Autowired
	Patho_StatusMaster_Repository repository;
	
	public BaseResponse save(Patho_StatusMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_StatusMaster_Entity entity = repository.findByStatusName(form.getStatusName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new Patho_StatusMaster_Entity();
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

	public BaseResponse update(Patho_StatusMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_StatusMaster_Entity entity = repository.findById(form.getId());
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
		Patho_StatusMaster_Entity entity = repository.findById(id);
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
