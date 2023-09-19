package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterTestMaster_IpdTest_DTO;
import com.as.h_entities.hims.MasterTestMaster_IpdTest_Entity;
import com.as.reporsitories.hims.MasterTestMaster_IpdTest_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
@Service
public class MasterTestMaster_IpdTest_Service {

	@Autowired
	MasterTestMaster_IpdTest_Repository repository;
	
	public BaseResponse save(MasterTestMaster_IpdTest_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterTestMaster_IpdTest_Entity entity = repository.findByTestName(form.getTestName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterTestMaster_IpdTest_Entity();
		
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

	public BaseResponse update(MasterTestMaster_IpdTest_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterTestMaster_IpdTest_Entity entity = repository.findById(form.getId());
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
		MasterTestMaster_IpdTest_Entity entity = repository.findById(id);
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
	
	@ReadOnlyProperty
	public BaseResponse getParticularsListByGroup(String groupName,String org,Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByGroupNameAndOrganizationName(groupName,org));
		return response;
	}
	
	public BaseResponse getParticularsListByOrganization(String organization, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByOrganizationName(organization));
		return response;
	}

	public BaseResponse getDailyTestList(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByDailyYn("Y"));
		return response;
	}
	
	
}
