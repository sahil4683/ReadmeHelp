package com.as.service.hims;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterPackageMaster_OpdPackageMaster_DTO;
import com.as.h_dto.hims.MasterPackageMaster_OpdPackageMaster_Details_DTO;
import com.as.h_entities.hims.MasterPackageMaster_OpdPackageMaster_Details_Entity;
import com.as.h_entities.hims.MasterPackageMaster_OpdPackageMaster_Entity;
import com.as.reporsitories.hims.MasterPackageMaster_OpdPackageMaster_Details_Repository;
import com.as.reporsitories.hims.MasterPackageMaster_OpdPackageMaster_Repository;
import com.as.reporsitories.hims.OrganizationMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class MasterPackageMaster_OpdPackageMaster_Service {

	@Autowired
	MasterPackageMaster_OpdPackageMaster_Details_Repository details_Repository;
	
	@Autowired
	MasterPackageMaster_OpdPackageMaster_Repository repository;
	
	@Autowired
	OrganizationMaster_Repository organizationMaster_Repository;
	
	public BaseResponse save(MasterPackageMaster_OpdPackageMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterPackageMaster_OpdPackageMaster_Entity entity = repository.findByPackageName(form.getPackageName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		
		entity = new MasterPackageMaster_OpdPackageMaster_Entity();
		
		form.setOrganizationName(organizationMaster_Repository.findById(Long.valueOf(form.getOrganizationCode())).getOrganization());;
		BeanUtils.copyProperties(form, entity);
		
		/*Other Modification On Entity
		 * */
		
		entity = repository.save(entity);
		
		for(MasterPackageMaster_OpdPackageMaster_Details_DTO detail: form.getDetails()) {
			MasterPackageMaster_OpdPackageMaster_Details_Entity details_Entity=new MasterPackageMaster_OpdPackageMaster_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setPackageId(String.valueOf(entity.getId()));
			details_Repository.save(details_Entity);
		}
		
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(MasterPackageMaster_OpdPackageMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterPackageMaster_OpdPackageMaster_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		for(MasterPackageMaster_OpdPackageMaster_Details_DTO detail: form.getDetails()) {
			MasterPackageMaster_OpdPackageMaster_Details_Entity details_Entity=details_Repository.findById(detail.getId());
			if(details_Entity != null) {
				BeanUtils.copyProperties(detail, details_Entity);
				details_Repository.save(details_Entity);
			}else {
				details_Entity = new MasterPackageMaster_OpdPackageMaster_Details_Entity();
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity.setPackageId(String.valueOf(form.getId()));
				details_Repository.save(details_Entity);
			}
			
		}
		
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
		MasterPackageMaster_OpdPackageMaster_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<MasterPackageMaster_OpdPackageMaster_Details_Entity> delete=details_Repository.findByPackageId(String.valueOf(entity.getId()));
			details_Repository.deleteAll(delete);
			
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

	public BaseResponse getDetailsById(String id, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(details_Repository.findByPackageId(id));
		return response;
	}

	public BaseResponse deleteDetailById(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		MasterPackageMaster_OpdPackageMaster_Details_Entity entity=details_Repository.findById(id);
		details_Repository.delete(entity);
		response.setBody("Deleted");
		return response;
	}
	
}
