package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters1_DoctorIncentiveMaster_DTO;
import com.as.h_entities.hims.MasterOtherMasters1_DoctorIncentiveMaster_Entity;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Department_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_DoctorIncentiveMaster_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
@Service
public class MasterOtherMasters1_DoctorIncentiveMaster_Service {
	
	@Autowired
	MasterOtherMasters1_DoctorIncentiveMaster_Repository repository;
	
	@Autowired
	MasterOtherMasters1_Department_Repository department_Repository;
	
	@Autowired
	ConsultantMaster_Repository consultantMaster_Repository;
	
	@Autowired
	MasterOtherMasters1_Group_Repository group_Repository;
	
	public BaseResponse save(MasterOtherMasters1_DoctorIncentiveMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_DoctorIncentiveMaster_Entity entity = repository.findByDoctorName(form.getDoctorName());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterOtherMasters1_DoctorIncentiveMaster_Entity();
		
		BeanUtils.copyProperties(form, entity);
		
		/*Other Modification On Entity
		 * */
		entity.setDoctorNameView(consultantMaster_Repository.findById(Long.valueOf(form.getDoctorName())).getName());
//		entity.setDepartmentView(department_Repository.findById(Long.valueOf(form.getDepartment())).getDept());
//		entity.setGroupNameView(group_Repository.findById(Long.valueOf(form.getGroupName())).getGroupName());
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(MasterOtherMasters1_DoctorIncentiveMaster_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_DoctorIncentiveMaster_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		entity.setDoctorNameView(consultantMaster_Repository.findById(Long.valueOf(form.getDoctorName())).getName());
//		entity.setDepartmentView(department_Repository.findById(Long.valueOf(form.getDepartment())).getDept());
//		entity.setGroupNameView(group_Repository.findById(Long.valueOf(form.getGroupName())).getGroupName());
		
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
		MasterOtherMasters1_DoctorIncentiveMaster_Entity entity = repository.findById(id);
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