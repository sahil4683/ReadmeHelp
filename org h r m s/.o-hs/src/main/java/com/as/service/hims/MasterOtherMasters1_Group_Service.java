package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters1_Group_DTO;
import com.as.h_entities.hims.MasterOtherMasters1_Group_Entity;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
@Service
public class MasterOtherMasters1_Group_Service {
	
	@Autowired
	MasterOtherMasters1_Group_Repository repository;
	
	public BaseResponse save(MasterOtherMasters1_Group_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_Group_Entity entity = repository.findByGroupName(form.getGroupName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterOtherMasters1_Group_Entity();
		
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

	public BaseResponse update(MasterOtherMasters1_Group_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_Group_Entity entity = repository.findById(form.getId());
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
		MasterOtherMasters1_Group_Entity entity = repository.findById(id);
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
		response.setBody(repository.findAll());
		return response;
	}

	public BaseResponse getGruopListByDepartment(long department, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByDepartment(String.valueOf(department)));
		return response;
	}

	public BaseResponse getGruopListByDepartmentAndSuperGroup(long department, long superGroup, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByDepartmentAndSuperGroup(String.valueOf(department), String.valueOf(superGroup)));
		return response;
	}
	
}
