package com.as.service.hims;

import java.security.Principal;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.MasterOtherMasters1_DeathSummaryTypeOrder_DTO;
import com.as.h_entities.hims.MasterOtherMasters1_DeathSummaryTypeOrder_Entity;
import com.as.h_entities.hims.MasterOtherMasters1_DeathSummaryType_Entity;
import com.as.reporsitories.hims.MasterOtherMasters1_DeathSummaryTypeOrder_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_DeathSummaryType_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import org.springframework.data.domain.Sort;
@Service
public class MasterOtherMasters1_DeathSummary_Service {

	@Autowired
	MasterOtherMasters1_DeathSummaryType_Repository type_Repository;

	@Autowired
	MasterOtherMasters1_DeathSummaryTypeOrder_Repository order_Repository;

	public BaseResponse add(String type, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_DeathSummaryType_Entity entity = type_Repository.findByType(type);
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new MasterOtherMasters1_DeathSummaryType_Entity();
		entity.setType(type);
		entity = type_Repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse delete(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_DeathSummaryType_Entity entity = type_Repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			type_Repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	@ReadOnlyProperty
	public BaseResponse getTypeList(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(type_Repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		return response;
	}

	public BaseResponse save(MasterOtherMasters1_DeathSummaryTypeOrder_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		MasterOtherMasters1_DeathSummaryTypeOrder_Entity entity = null;

		order_Repository.deleteAll();

		StringTokenizer tokenizer = new StringTokenizer(form.getTypeId(), "_");
		while (tokenizer.hasMoreElements()) {
			entity = new MasterOtherMasters1_DeathSummaryTypeOrder_Entity();
			entity.setFooter(form.getFooter());
			entity.setTypeId(tokenizer.nextToken());
			order_Repository.save(entity);
		}

		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Update");
			return response;
		}
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(order_Repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		return response;
	}

}
