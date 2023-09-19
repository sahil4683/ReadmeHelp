package com.as.service.hims;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.as.entities.AccountYear_Entity;
import com.as.reporsitories.hims.AccountYearRepository;
import com.as.request.AccountYear_Form;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.utils.ClientRequestInfo;

@Service
public class AccountYearService {

	@Autowired
	AccountYearRepository repository;

	public BaseResponse save(AccountYear_Form form, Principal principal) {
		BaseResponse response = new BaseResponse();
		AccountYear_Entity entity = repository.findByAcYear(form.getAcYear());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new AccountYear_Entity();
		BeanUtils.copyProperties(form, entity);
		entity.setFromDate(entity.getFromDate() + " 00:00:00");
		entity.setToDate(entity.getToDate() + " 23:59:59");
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(AccountYear_Form form, Principal principal) {
		BaseResponse response = new BaseResponse();
		int id = Integer.parseInt(form.getId());
		AccountYear_Entity entity = repository.findById(id);
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

	public BaseResponse delete(int id, Principal principal) {
		BaseResponse response = new BaseResponse();
		AccountYear_Entity entity = repository.findById(id);
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

	public BaseResponse findAll(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		return response;
	}

	@Transactional
	public Long getCreditYearId() {
		// Setting Account Year...
		String currentTimestamp = ClientRequestInfo.getCurentDateAndTime();
		AccountYear_Entity entiry = repository.findByFromDateGreaterThanEqualAndToDateLessThanEqual(currentTimestamp);
		return entiry.getId();
	}

	public BaseResponse setDefalut(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		List<AccountYear_Entity> entityList = repository.findAll();
		if (entityList != null && !entityList.isEmpty()) {
			for(AccountYear_Entity entity: entityList) {
				if(entity.getId() == id) {
					entity.setDefault(true);	
				}else {
					entity.setDefault(false);	
				}
			}
			repository.saveAll(entityList);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Default Year Updated");
			return response;
			
		} else {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
	}

}
