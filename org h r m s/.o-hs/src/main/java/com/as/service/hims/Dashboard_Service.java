package com.as.service.hims;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class Dashboard_Service {

	@Autowired
	ReceptionReceptionRegistration_Repository repository;
	
	@ReadOnlyProperty
	public BaseResponse getCount(Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getCount(creditYear));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getRegistredCount(Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getRegistredCount(creditYear));
		return response;
	}
	
}
