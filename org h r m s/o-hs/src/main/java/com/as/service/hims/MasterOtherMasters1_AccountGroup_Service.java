package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.MasterOtherMasters1_AccountGroup_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class MasterOtherMasters1_AccountGroup_Service {

	@Autowired
	MasterOtherMasters1_AccountGroup_Repository repository;
	
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
