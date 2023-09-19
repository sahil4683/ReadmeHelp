package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.entities.NumberSequenceUtility;
import com.as.h_dto.hims.AccountEntry_Receipt_DTO;
import com.as.h_entities.hims.AccountEntry_Receipt_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.AccountEntry_Receipt_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
@Service
public class AccountEntry_Receipt_Service {

	@Autowired
	AccountEntry_Receipt_Repository repository;
	
	@Autowired
	ReceptionReceptionRegistration_Repository registrationRepository; 
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;
	
	@Autowired
	NumberSequenceUtilityRepository sequenceUtilityRepository;
	
	public BaseResponse save(AccountEntry_Receipt_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
//		AccountEntry_Receipt_Entity entity = repository.findByipdno(form.getIpdno());
//		if (entity != null) {
//			response.setStatus(300);
//			response.setType(ResponseType.WARNING);
//			response.setMessage("Already Exits");
//			return response;
//		}
		AccountEntry_Receipt_Entity entity = new AccountEntry_Receipt_Entity();
		
		BeanUtils.copyProperties(form, entity);
		
	
		/*Other Modification On Entity
		 * */
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("Receipt");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();
		utility.setSequence(nextSequence+1);
		sequenceUtilityRepository.save(utility);

		entity.setReceiptNo(prefix+""+nextSequence+""+suffix);

		entity.setUserName(principal.getName());
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(AccountEntry_Receipt_DTO form,Long creditYear, Principal principal) {
		BaseResponse response = new BaseResponse();
		AccountEntry_Receipt_Entity entity = repository.findByIdAndCreditYear(form.getId(), creditYear);
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

	public BaseResponse delete(long id,Long creditYear, Principal principal) {
		BaseResponse response = new BaseResponse();
		AccountEntry_Receipt_Entity entity = repository.findByIdAndCreditYear(id,creditYear);
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
	public BaseResponse findAll(Principal principal,Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc(yearCd));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Principal principal, Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByReceiptDateAndCreditYear(Date,yearCd));
		return response;
	}
	
	public byte[] report_PDF(String uhid,Long creditYear, String format) {
		try {
			String templetSrc = env.getProperty("receipt.report.file");
		
			AccountEntry_Receipt_Entity dataList = repository.findByIdAndCreditYear(Long.valueOf(uhid),creditYear);
			
			ReceptionReceptionRegistration_Entity registration= registrationRepository.findByUHID(dataList.getUhid());
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("receiptNo", dataList.getReceiptNo());
			parameters.put("receiptDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(dataList.getReceiptDate()));
			if(dataList.getType().equals("PLASTICMONEY")) {
				parameters.put("mode", dataList.getType()+ " ("+dataList.getPlasticInstrumentName()+")");
			}else {
				parameters.put("mode", dataList.getType());
			}
			parameters.put("patientName", dataList.getReceivedFrom());
			parameters.put("amount", dataList.getAmount());
			parameters.put("words", dataList.getWords());
			parameters.put("uhid", dataList.getUhid());
			parameters.put("ipdNo", dataList.getIpdno());
			parameters.put("userName", dataList.getUserName());
			parameters.put("against", dataList.getAgainst());
			
			parameters.put("admissionDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(registration.getDate()));
			parameters.put("address", registration.getAddress());
			parameters.put("area", registration.getArea()!=null?registration.getArea():" ");
			parameters.put("city", registration.getCity()!=null?registration.getCity():" ");
			
			return factory.getPdfFormat(templetSrc, format, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public BaseResponse findByIpdno(String ipdno, Principal principal, Long yearCd) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.sumByIpdno(ipdno,yearCd));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse searchOnTableData(String searchtext,Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.searchOnTableData(searchtext,creditYear));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getOnTableData(Long searchtext,Principal principal, Long creditYear) {
		
		AccountEntry_Receipt_Entity entity = repository.findByIdAndCreditYear(searchtext,creditYear);
		ArrayList<AccountEntry_Receipt_Entity> array=new ArrayList<>();
		if(entity!=null) {
			array.add(entity);
		}
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}
	
}
