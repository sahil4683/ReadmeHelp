package com.as.service.hims;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.DeathSummary_DTO;
import com.as.h_entities.hims.DeathSummary_Entity;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.DeathSummary_Repository;
import com.as.reporsitories.hims.DoctorReference_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
import com.as.utils.TimeFormatChanger;
@Service
public class DeathSummary_Service {

	@Autowired
	DeathSummary_Repository repository;
	
	@Autowired
	ConsultantMaster_Repository consultantRepository;
	
	@Autowired
	DoctorReference_Repository referenceRepository;
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;
	
	public BaseResponse save(DeathSummary_DTO form, Principal principal,Long year) {
		BaseResponse response = new BaseResponse();
		DeathSummary_Entity entity = repository.findByIpdnoAndUhidAndCreditYear(form.getIpdno(),form.getUhid(),year);
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
//		DeathSummary_Entity entity = new DeathSummary_Entity();
		entity = new DeathSummary_Entity();
		
		BeanUtils.copyProperties(form, entity);

		
		/*Other Modification On Entity
		 * */
		
		if(form.getConsultant1() !=null && form.getConsultant1() !="0" && !form.getConsultant1().equals("0")) {
			entity.setConsultant1(consultantRepository.findById(Long.valueOf(form.getConsultant1())).getName());}
		if(form.getConsultant2() !=null && form.getConsultant2() !="0" && !form.getConsultant2().equals("0")) {
			entity.setConsultant2(consultantRepository.findById(Long.valueOf(form.getConsultant2())).getName());}
		if(form.getConsultant3() !=null && form.getConsultant3() !="0" && !form.getConsultant3().equals("0")) {
			entity.setConsultant3(consultantRepository.findById(Long.valueOf(form.getConsultant3())).getName());}
		if(form.getConsultant4() !=null && form.getConsultant4() !="0" && !form.getConsultant4().equals("0")) {
			entity.setConsultant4(consultantRepository.findById(Long.valueOf(form.getConsultant4())).getName());}
		
		if(form.getRefBy1() !=null && form.getRefBy1() !="0" && !form.getRefBy1().equals("0")) {
			entity.setRefBy1(consultantRepository.findById(Long.valueOf(form.getRefBy1())).getName());}
		if(form.getRefBy2() !=null && form.getRefBy2() !="0" && !form.getRefBy2().equals("0")) {
			entity.setRefBy2(consultantRepository.findById(Long.valueOf(form.getRefBy2())).getName());}
		if(form.getRefBy3() !=null && form.getRefBy3() !="0" && !form.getRefBy3().equals("0")) {
			entity.setRefBy3(consultantRepository.findById(Long.valueOf(form.getRefBy3())).getName());}
		if(form.getRefBy4() !=null && form.getRefBy4() !="0" && !form.getRefBy4().equals("0")) {
			entity.setRefBy4(consultantRepository.findById(Long.valueOf(form.getRefBy4())).getName());}
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(DeathSummary_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		DeathSummary_Entity entity = repository.findById(form.getId());
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
		DeathSummary_Entity entity = repository.findById(id);
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
	public BaseResponse findAll(Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc(creditYear));
		return response;
	}
	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByProcedureDateAndCreditYearOrderByIdDesc(Date,creditYear));
		return response;
	}

	public byte[] report_PDF(String id, String format,Principal principal) {
		try {			
			String templetSrc = env.getProperty("ipd.death_summary.file");
			
			DeathSummary_Entity summList = repository.findById(Long.valueOf(id));

			Map<String, Object> parameters = new HashMap<>();

			parameters.put("consultant1", summList.getConsultant1());
			parameters.put("consultant2", summList.getConsultant2()!=null&&summList.getConsultant2()!="0"?summList.getConsultant2():"");
			parameters.put("consultant3", summList.getConsultant3()!=null?summList.getConsultant3():"");
			parameters.put("consultant4", summList.getConsultant4()!=null?summList.getConsultant4():"");
			
			parameters.put("uhid", summList.getUhid());
			parameters.put("ipdno", summList.getIpdno());
			parameters.put("patientName", summList.getName());
			parameters.put("address", summList.getAddress());
			parameters.put("admissionDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(summList.getAdmissionDate()));
			parameters.put("admissionTime", TimeFormatChanger.Time24HourTo12Hour(summList.getAdmissionTime()));
			parameters.put("dischargeDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(summList.getDischargeDate()));
			parameters.put("dischargeTime", TimeFormatChanger.Time24HourTo12Hour(summList.getDischargeTime()));
			parameters.put("caseType", summList.getCaseType());
			parameters.put("age", summList.getAge() +" / "+summList.getSex());
			parameters.put("userName", principal.getName());
			parameters.put("summary1", summList.getSummary1());
			parameters.put("summary2", summList.getSummary2());
			parameters.put("footer", summList.getFooter());
			
			return factory.getPdfFormat(templetSrc, format, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
