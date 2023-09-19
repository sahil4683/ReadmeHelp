package com.as.service.hims;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.RequestSheetIpd_DTO;
import com.as.h_dto.hims.RequestSheetIpd_Details_DTO;
import com.as.h_entities.hims.RequestSheetIpd_Details_Entity;
import com.as.h_entities.hims.RequestSheetIpd_Entity;
import com.as.reporsitories.hims.RequestSheetIpd_Details_Repository;
import com.as.reporsitories.hims.RequestSheetIpd_Repository;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;

@Service
public class RequestSheetIpd_Service {
	
	
	private static final Logger log = LoggerFactory.getLogger(RequestSheetIpd_Service.class);


	@Autowired
	RequestSheetIpd_Details_Repository details_Repository;
	
	@Autowired
	RequestSheetIpd_Repository repository;
	
	public BaseResponse save(RequestSheetIpd_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
//		RequestSheetIpd_Entity entity = repository.findByDept(form.getDept());
//		if (entity != null) {
//			response.setStatus(300);
//			response.setType(ResponseType.WARNING);
//			response.setMessage("Already Exits");
//			return response;
//		}
		RequestSheetIpd_Entity entity = new RequestSheetIpd_Entity();
		
		BeanUtils.copyProperties(form, entity);
		
		/*Other Modification On Entity
		 * */
		entity = repository.save(entity);
		
		for(RequestSheetIpd_Details_DTO detail: form.getDetails()) {
			detail.setId(null);
			RequestSheetIpd_Details_Entity details_Entity=new RequestSheetIpd_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setRequestId(String.valueOf(entity.getId()));
			details_Entity.setRequestBy(principal.getName());
			details_Repository.save(details_Entity);
		}
		log.warn("User : "+principal.getName()+"\t Added : "+entity);
		log.warn("User : "+principal.getName()+"\t Added : "+form.getDetails());
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(RequestSheetIpd_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		RequestSheetIpd_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);
		
		for(RequestSheetIpd_Details_DTO detail: form.getDetails()) {
			RequestSheetIpd_Details_Entity details_Entity=details_Repository.findById(detail.getId());
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

	public BaseResponse delete(long id, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		RequestSheetIpd_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<RequestSheetIpd_Details_Entity> delete=details_Repository.findByRequestIdAndCreditYear(String.valueOf(entity.getId()),creditYear);
			details_Repository.deleteAll(delete);
			
			repository.delete(entity);
			
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}
	
	public BaseResponse deleteDetail(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		RequestSheetIpd_Details_Entity entity = details_Repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			details_Repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal,Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc(yearCd));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getDoneTestOfPatient(String ipdno, Principal principal, Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getDoneTestOfPatient(yearCd,ipdno));
		return response;
	}

	public BaseResponse deleteDoneTestOfPatient(long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		RequestSheetIpd_Details_Entity entity = details_Repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			log.warn("User : "+principal.getName()+"\t Deleted : "+entity);
			details_Repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}
	
}
