package com.as.service.hims;
import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters2_WardMaster_DTO;
import com.as.h_entities.hims.MasterOtherMasters2_ClassMaster_Entity;
import com.as.h_entities.hims.MasterOtherMasters2_WardMaster_Entity;
import com.as.reporsitories.hims.MasterOtherMasters2_ClassMaster_Repository;
import com.as.reporsitories.hims.MasterOtherMasters2_WardMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class MasterOtherMasters2_WardMaster_Service {

	@Autowired
	MasterOtherMasters2_WardMaster_Repository repository;
	
	@Autowired
	MasterOtherMasters2_ClassMaster_Repository classMaster_Repository;
	
	public BaseResponse save(MasterOtherMasters2_WardMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters2_WardMaster_Entity entity = repository.findByWardName(form.getWardName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterOtherMasters2_WardMaster_Entity();
		
		BeanUtils.copyProperties(form, entity);

//		entity.setClassid(classMaster_Repository.findByClassName(form.getClassName()).getId().toString());
		MasterOtherMasters2_ClassMaster_Entity classs = classMaster_Repository.findById(Long.valueOf(form.getClassid()));
		entity.setClassName(classs.getClassName());
		
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

	public BaseResponse update(MasterOtherMasters2_WardMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters2_WardMaster_Entity entity = repository.findById(form.getId());
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
		MasterOtherMasters2_WardMaster_Entity entity = repository.findById(id);
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
