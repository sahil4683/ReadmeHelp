package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.IpdDischange_DischargeClearance_DTO;
import com.as.h_entities.hims.Discharge_Entity;
import com.as.reporsitories.hims.IpdDischange_DischargeClearance_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
@Service
public class IpdDischange_DischargeClearance_Service {

	@Autowired
	IpdDischange_DischargeClearance_Repository repository;
	
	public BaseResponse save(IpdDischange_DischargeClearance_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Discharge_Entity entity = new Discharge_Entity();
		
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

	public BaseResponse update(IpdDischange_DischargeClearance_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		Discharge_Entity entity = repository.findById(form.getId());
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
		Discharge_Entity entity = repository.findById(id);
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
	public BaseResponse findAll(Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc(creditYear));
		return response;
	}
	
}
