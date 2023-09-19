package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.entities.NumberSequenceUtility;
import com.as.h_dto.hims.ReceptionReception_Admission_DTO;
import com.as.h_entities.hims.BedMaster;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.BedMasterRepository;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.OrganizationMaster_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
import com.as.utils.TimeFormatChanger;

@Service
public class ReceptionReceptionAdmission_Service {

	@Autowired
	ReceptionReceptionAdmission_Repository repository;

	@Autowired
	BedMasterRepository bedMasterRepository;

	@Autowired
	ReceptionReceptionRegistration_Repository registration_Repository;

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

	public BaseResponse save(ReceptionReception_Admission_DTO form, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		ReceptionReceptionAdmission_Entity entity = repository.findByIPDNOAndCreditYear(form.getIPDNO(),creditYear);
//		ReceptionReceptionAdmission_Entity entity = null;
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new ReceptionReceptionAdmission_Entity();

		BeanUtils.copyProperties(form, entity);


		/*
		 * Other Modification On Entity
		 */
//		
//		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("");
//		Long nextIPDNO = utility.getSequence();
//		nextIPDNO = (nextIPDNO == null ? 1 : nextIPDNO);
//		utility.setSequence(nextIPDNO+1);
//		sequenceUtilityRepository.save(utility);
		
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("IPD");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();
		utility.setSequence(nextSequence+1);
		sequenceUtilityRepository.save(utility);

		entity.setIPDNO(prefix+""+nextSequence+""+suffix);

		entity.setUserName(principal.getName());
		entity.setDischarge("NO");
		entity = repository.save(entity);

		// ************* Update Bed Status *************
		BedMaster bedEntity = bedMasterRepository.findByBedno(form.getBEDNO());
		bedEntity.setBookedyn(1);
		bedMasterRepository.save(bedEntity);

		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			response.setBody(entity.getId());
			return response;
		}
		return response;
	}

	public BaseResponse update(ReceptionReception_Admission_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionReceptionAdmission_Entity entity = repository.findById(form.getId());
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
		ReceptionReceptionAdmission_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			// ************* Release Bed Status *************
			BedMaster bedEntity = bedMasterRepository.findByBedno(entity.getBEDNO());
			bedEntity.setBookedyn(0);
			bedMasterRepository.save(bedEntity);

			repository.delete(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal,Long creditYear) {
		List<Object[]> results = repository.getFullPatientDetail(creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admission", objects[0]);
			map.put("registration", objects[1]);
			array.add(map);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}

//	public BaseResponse getNextIPD(Principal principal) {
//		BaseResponse response = new BaseResponse();
//		response.setStatus(200);
//		response.setType(ResponseType.SUCCESS);
//		response.setMessage("List");
//		Long nextToken = repository.getNextIPD();
//		nextToken = (nextToken == null ? 1 : nextToken + 1);
//		response.setBody(nextToken);
//		return response;
//	}

	public BaseResponse getPatientDetailsByIPD(String ipd, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByIPDNOAndCreditYear_Details(ipd,creditYear));
		return response;
	}

	public BaseResponse getFullPatientDetailByIPD(String ipd, Principal principal,Long creditYear) {
		List<Object[]> results = repository.getFullPatientDetailByIPD(ipd,creditYear);
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object[] result : results) {
			map.put("admission", result[0]);
			map.put("registration", result[1]);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(map);
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Principal principal,Long creditYear) {
		List<Object[]> results = repository.getFullPatientDetailByDate(Date,creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admission", objects[0]);
			map.put("registration", objects[1]);
			array.add(map);
		}
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getPatientDetailList(Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getNameUhidIpd(creditYear));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getAdmitedPatient(String searchtext, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getAdmitedPatientByStatus(searchtext,creditYear));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getPatientDetailListWithDischarge(Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getDischargeJoinList());
		return response;
	}

	public byte[] report_PDF(String id, String format, String type) {
		try {
			String templetSrc = "";
			ReceptionReceptionAdmission_Entity dataList = repository.findById(Long.valueOf(id));
			ReceptionReceptionRegistration_Entity registraionLit = registration_Repository.findByUHID(dataList.getUHID());
			Map<String, Object> parameters = new HashMap<>();
			if (type.equals("PrintGatePass")) {
				templetSrc = env.getProperty("admission.PrintGatePass.report.file");
				parameters.put("patientName", registraionLit.getName());
				parameters.put("ipdNo", String.valueOf(dataList.getIPDNO()));
				parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(dataList.getDate()));
				parameters.put("allow", dataList.getWitness1());
			}
			if (type.equals("ExportIPDLable")) {
				templetSrc = env.getProperty("admission.ExportIPDLable.report.file");
				parameters.put("patientName", registraionLit.getName());
				parameters.put("ipdNo", String.valueOf(dataList.getIPDNO()));
				parameters.put("uhid", String.valueOf(dataList.getUHID()));
				parameters.put("mobile", registraionLit.getMobile());
				parameters.put("age", registraionLit.getAge()!=null?registraionLit.getAge():"" + "/" + registraionLit.getSex());
				parameters.put("dob", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(registraionLit.getDOB()));
				parameters.put("bedNo", dataList.getBEDNO());
				parameters.put("consultant",
						consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1())).getName());
			}
			if (type.equals("IPDCaseRecordLable")) {
				templetSrc = env.getProperty("admission.IPDCaseRecordLable.report.file");
				parameters.put("patientName", registraionLit.getName());
				parameters.put("ipdNo", String.valueOf(dataList.getIPDNO()));
				parameters.put("uhid", String.valueOf(dataList.getUHID()));
				parameters.put("mobile", registraionLit.getMobile());
				parameters.put("age", registraionLit.getAge()!=null?registraionLit.getAge():"" + "/" + registraionLit.getSex());
				parameters.put("dob", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(registraionLit.getDOB()));
				parameters.put("bedNo", dataList.getBEDNO());
				parameters.put("class", dataList.getWardname());
				parameters.put("type", dataList.getTypeofPatient());
				parameters.put("organization", organizationMaster_Repository.findById(Long.valueOf(dataList.getOrganization())).getOrganization());
				parameters.put("consultant", consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1())).getName());
				parameters.put("address", registraionLit.getAddress() + ", " + registraionLit.getCity());
				parameters.put("witness", dataList.getWitness1() + ", " + dataList.getWitness2());
				parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(dataList.getDate()));
				parameters.put("time", TimeFormatChanger.Time24HourTo12Hour(dataList.getTime()));
				parameters.put("phone", dataList.getContact1()!=null?dataList.getContact1():"");
			}
			if (type.equals("PrintIPDCaseRecord")) {
				templetSrc = env.getProperty("admission.IPDCaseRecordLable.report.file");
				parameters.put("patientName", registraionLit.getName());
				parameters.put("ipdNo", String.valueOf(dataList.getIPDNO()));
				parameters.put("uhid", String.valueOf(dataList.getUHID()));
				parameters.put("mobile", registraionLit.getMobile());
				parameters.put("age", registraionLit.getAge()!=null?registraionLit.getAge():"" + "/" + registraionLit.getSex());
				parameters.put("dob", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(registraionLit.getDOB()));
				parameters.put("bedNo", dataList.getBEDNO());
				parameters.put("class", dataList.getWardname());
				parameters.put("type", dataList.getTypeofPatient());
				parameters.put("organization", organizationMaster_Repository.findById(Long.valueOf(dataList.getOrganization())).getOrganization());
				parameters.put("consultant", consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1())).getName());
				parameters.put("address", registraionLit.getAddress() + ", " + registraionLit.getCity());
				parameters.put("witness", dataList.getWitness1() + ", " + dataList.getWitness2());
				parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(dataList.getDate()));
				parameters.put("time", TimeFormatChanger.Time24HourTo12Hour(dataList.getTime()));
				parameters.put("phone", dataList.getContact1()!=null?dataList.getContact1():"");
			}
			return factory.getPdfFormat(templetSrc, format, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		
		List<Object[]> results = repository.getFullPatientDetailById(searchtext,creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("admission", objects[0]);
			map.put("registration", objects[1]);
			array.add(map);
		}
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}

}
