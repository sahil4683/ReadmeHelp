package com.as.service.hims;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.as.entities.NumberSequenceUtility;
import com.as.h_dto.hims.ReceptionReception_Registration_DTO;
import com.as.h_entities.hims.ConsultantMaster_Entity;
import com.as.h_entities.hims.MasterOtherMasters1_CasepaperMedicineMaster_Entity;
import com.as.h_entities.hims.OrganizationMaster_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_CasepaperMedicineMaster_Repository;
import com.as.reporsitories.hims.OrganizationMaster_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
@Service
public class ReceptionReceptionRegistration_Service {
	
	@Autowired
	ReceptionReceptionRegistration_Repository repository;
	
	@Autowired
	MasterOtherMasters1_CasepaperMedicineMaster_Repository casepaperMedicineMaster_Repository;
	
	@Autowired
	OrganizationMaster_Repository organizationMaster_Repository;
	
	@Autowired
	ConsultantMaster_Repository consultantMaster_Repository;
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;
	
	@Autowired
	NumberSequenceUtilityRepository sequenceUtilityRepository;
	
	public void setNextUhid() {
		
	}

	public BaseResponse save(ReceptionReception_Registration_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionReceptionRegistration_Entity entity = repository.findByUHID(form.getUHID());
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new ReceptionReceptionRegistration_Entity();
		BeanUtils.copyProperties(form, entity);

		/*Other Modification On Entity
		 * */
		
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("UHID");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();
		utility.setSequence(nextSequence+1);
		sequenceUtilityRepository.save(utility);

		entity.setUHID(prefix+""+nextSequence+""+suffix);
		entity.setCASEN(prefix+""+nextSequence+""+suffix);
		
		Long nextToken = repository.getNextTOKEN(LocalDate.now().toString());
		nextToken = (nextToken == null ? 1 : nextToken+1);
		
		entity.setTOKENNO(String.valueOf(nextToken));
		entity.setUserName(principal.getName());
		entity.setExpired("NO");
		entity.setExporedYN("NO");
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			response.setBody(entity.getId());
			return response;
		}
		return response;
	}

	public BaseResponse update(ReceptionReception_Registration_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionReceptionRegistration_Entity entity = repository.findById(form.getId());
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
		ReceptionReceptionRegistration_Entity entity = repository.findById(id);
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
	public BaseResponse getCurrent(String Date, Principal principal) {
		
		List<ReceptionReceptionRegistration_Entity> list = repository.findByDate(Date);
		
		List<ReceptionReception_Registration_DTO> dto_list = new ArrayList<ReceptionReception_Registration_DTO>();
		
		for (ReceptionReceptionRegistration_Entity item : list) {
			ReceptionReception_Registration_DTO dto = new ReceptionReception_Registration_DTO();
			BeanUtils.copyProperties(item, dto);
			
			ConsultantMaster_Entity consultant  = consultantMaster_Repository.findById(Long.valueOf(item.getConsultant()));
			dto.setConsultantName( consultant!=null ? consultant.getName() : "0");
			
			OrganizationMaster_Entity organization = organizationMaster_Repository.findById(Long.valueOf(item.getOrganization()));
			dto.setOrganizationName(organization!=null ? organization.getOrganization() : "0");
			dto_list.add(dto);
		}

		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(dto_list);
		return response;
	}

	public BaseResponse getNextMLC(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		Long nextMLC = repository.getNextMLC();
		nextMLC = (nextMLC == null ? 1 : nextMLC+1);
		response.setBody(nextMLC);
		return response;
	}

	public BaseResponse getNextTOKEN(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		Long nextToken = repository.getNextTOKEN(LocalDate.now().toString());
		nextToken = (nextToken == null ? 1 : nextToken+1);
		response.setBody(nextToken);
		return response;
	}

	public BaseResponse getPatientDetailsByUHID(String uhid, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByUHID(uhid));
		return response;
	}
	
	public byte[] report_PDF(String id, String format, String type) {
			try {
				String templetSrc = "";
				ReceptionReceptionRegistration_Entity dataList = repository.findById(Long.valueOf(id));
				List<MasterOtherMasters1_CasepaperMedicineMaster_Entity> casepaperMedicineMaster = null;
			
				if(type.equals("CasePaperMedicine")) {
					templetSrc = env.getProperty("registration.CasePaperMedicine.report.file");
					casepaperMedicineMaster = casepaperMedicineMaster_Repository.findAll();
				}
				if(type.equals("PrintOPDCasePaper")) {
					templetSrc = env.getProperty("registration.PrintOPDCasePaper.report.file");
				}
				if(type.equals("PrintLable")) {
					templetSrc = env.getProperty("registration.PrintLable.report.file");
				}
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("patientName", dataList.getName());
				parameters.put("uhid", String.valueOf(dataList.getUHID()));
				parameters.put("organization", organizationMaster_Repository.findById(Long.valueOf(dataList.getOrganization())).getOrganization());
				parameters.put("consultant", consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant())).getName());
				parameters.put("age", dataList.getAge()!=null?dataList.getAge():""+"/"+dataList.getSex());
				parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(dataList.getDate()));
				parameters.put("mobile", dataList.getMobile());
				parameters.put("tokenNo", dataList.getTOKENNO());
				parameters.put("userName", dataList.getUserName());
				parameters.put("address", dataList.getAddress()+", "+dataList.getCity());
			
				return factory.getPdfFormat(templetSrc, format, parameters, casepaperMedicineMaster);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}

//	@ReadOnlyProperty
//	public BaseResponse getUhidName(Principal principal) {
//		BaseResponse response = new BaseResponse();
//		response.setStatus(200);
//		response.setType(ResponseType.SUCCESS);
//		response.setMessage("List");
//		response.setBody(repository.getUhidName());
//		return response;
//	}
	
	@ReadOnlyProperty
	public BaseResponse filterUhidName(String searchtext,Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.filterUhidName(searchtext));
		return response;
	}
	
	
	@ReadOnlyProperty
	public BaseResponse searchOnTableData(String searchtext,Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.searchOnTableData(searchtext));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getOnTableData(Long searchtext,Principal principal, Long creditYear) {
		
//		ReceptionReceptionRegistration_Entity entity = repository.findById(searchtext);
//		ArrayList<Object> array=new ArrayList<Object>();
//		array.add(entity);
		ReceptionReceptionRegistration_Entity entity = repository.findById(searchtext);
		
		List<ReceptionReception_Registration_DTO> dto_list = new ArrayList<ReceptionReception_Registration_DTO>();
		
		if(entity != null) {
			ReceptionReception_Registration_DTO dto = new ReceptionReception_Registration_DTO();
			BeanUtils.copyProperties(entity, dto);
			
			ConsultantMaster_Entity consultant  = consultantMaster_Repository.findById(Long.valueOf(entity.getConsultant()));
			dto.setConsultantName( consultant!=null ? consultant.getName() : "0");
			
			OrganizationMaster_Entity organization = organizationMaster_Repository.findById(Long.valueOf(entity.getOrganization()));
			dto.setOrganizationName(organization!=null ? organization.getOrganization() : "0");
			dto_list.add(dto);
		}
		
		
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(dto_list);
		return response;
	}

}
