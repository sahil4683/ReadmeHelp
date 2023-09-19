package com.as.service.hims;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_DTO;
import com.as.h_dto.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Details_DTO;
import com.as.h_entities.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity;
import com.as.h_entities.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Entity;
import com.as.reporsitories.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_RefDoctorIncentiveMaster_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class MasterOtherMasters1_RefDoctorIncentiveMaster_Service {

	@Autowired
	MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Repository details_Repository;
	
	@Autowired
	MasterOtherMasters1_RefDoctorIncentiveMaster_Repository repository;
	
	public BaseResponse save(MasterOtherMasters1_RefDoctorIncentiveMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
//		MasterOtherMasters1_RefDoctorIncentiveMaster_Entity entity = repository.findByDept(form.getDept());
//		if (entity != null) {
//			response.setStatus(300);
//			response.setType(ResponseType.WARNING);
//			response.setMessage("Already Exits");
//			return response;
//		}
		MasterOtherMasters1_RefDoctorIncentiveMaster_Entity entity = new MasterOtherMasters1_RefDoctorIncentiveMaster_Entity();
		
		BeanUtils.copyProperties(form, entity);
		
		/*Other Modification On Entity
		 * */
		
		for(MasterOtherMasters1_RefDoctorIncentiveMaster_Details_DTO detail: form.getDetails()) {
			MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity details_Entity=new MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Repository.save(details_Entity);
		}
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(MasterOtherMasters1_RefDoctorIncentiveMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_RefDoctorIncentiveMaster_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		for(MasterOtherMasters1_RefDoctorIncentiveMaster_Details_DTO detail: form.getDetails()) {
			MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity details_Entity=details_Repository.findById(detail.getId());
			BeanUtils.copyProperties(detail, details_Entity);
			details_Repository.save(details_Entity);
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
		MasterOtherMasters1_RefDoctorIncentiveMaster_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<MasterOtherMasters1_RefDoctorIncentiveMaster_Details_Entity> delete=details_Repository.findByIncMasterId(String.valueOf(entity.getId()));
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
}
