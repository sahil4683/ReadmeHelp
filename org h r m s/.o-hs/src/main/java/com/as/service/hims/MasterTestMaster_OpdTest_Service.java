package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterBillMaster_BillMaster_DTO;
import com.as.h_dto.hims.MasterTestMaster_OpdTest_DTO;
import com.as.h_entities.hims.MasterTestMaster_OpdTest_Entity;
import com.as.reporsitories.hims.MasterTestMaster_OpdTest_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class MasterTestMaster_OpdTest_Service {

	@Autowired
	MasterTestMaster_OpdTest_Repository repository;
	
	public BaseResponse save(MasterTestMaster_OpdTest_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterTestMaster_OpdTest_Entity entity = repository.findByTestName(form.getTestName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterTestMaster_OpdTest_Entity();
		
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

	public BaseResponse update(MasterTestMaster_OpdTest_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterTestMaster_OpdTest_Entity entity = repository.findById(form.getId());
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
		MasterTestMaster_OpdTest_Entity entity = repository.findById(id);
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
	public BaseResponse getParticularsListByGroup(String groupName,Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByGroupName(groupName));
		return response;
	}

	public BaseResponse updateRate(MasterBillMaster_BillMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterTestMaster_OpdTest_Entity entity = repository.findById(form.getTestName());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		
		entity.setRate(form.getRate());
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
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

	public BaseResponse getParticularsListByGroupAndOrganization(String group, String organization,
			Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByGroupNameAndOrganizationName(group,organization));
		return response;
	}
	
}
