package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters2_TpaMaster_DTO;
import com.as.h_entities.hims.MasterOtherMasters2_TpaMaster_Entity;
import com.as.reporsitories.hims.MasterOtherMasters2_TpaMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class MasterOtherMasters2_TpaMaster_Service {

	@Autowired
	MasterOtherMasters2_TpaMaster_Repository repository;
	
	public BaseResponse save(MasterOtherMasters2_TpaMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters2_TpaMaster_Entity entity = repository.findByTpaName(form.getTpaName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterOtherMasters2_TpaMaster_Entity();
		
		BeanUtils.copyProperties(form, entity);

		
		/*Other Modification On Entity
		 * */
		entity.setUserName(principal.getName());
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(MasterOtherMasters2_TpaMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters2_TpaMaster_Entity entity = repository.findById(form.getId());
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
		MasterOtherMasters2_TpaMaster_Entity entity = repository.findById(id);
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
