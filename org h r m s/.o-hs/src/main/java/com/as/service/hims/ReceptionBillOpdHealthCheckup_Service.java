package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.entities.NumberSequenceUtility;
import com.as.h_dto.hims.ReceptionBillOpdHealthCheckup_DTO;
import com.as.h_dto.hims.ReceptionBillOpdHealthCheckup_Details_DTO;
import com.as.h_entities.hims.DoctorReference_Entity;
import com.as.h_entities.hims.ReceptionBillOpdHealthCheckup_Details_Entity;
import com.as.h_entities.hims.ReceptionBillOpdHealthCheckup_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.DoctorReference_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_SubDepartment_Repository;
import com.as.reporsitories.hims.MasterTestMaster_OpdTest_Repository;
import com.as.reporsitories.hims.OrganizationMaster_Repository;
import com.as.reporsitories.hims.ReceptionBillOpdHealthCheckup_Details_Repository;
import com.as.reporsitories.hims.ReceptionBillOpdHealthCheckup_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ReceptionBillMap;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
@Service
public class ReceptionBillOpdHealthCheckup_Service {

	@Autowired
	ReceptionBillOpdHealthCheckup_Details_Repository details_Repository;

	@Autowired
	ReceptionBillOpdHealthCheckup_Repository repository;

	@Autowired
	MasterOtherMasters1_Group_Repository group_Repository;

	@Autowired
	ConsultantMaster_Repository consultantMaster_Repository;

	@Autowired
	MasterTestMaster_OpdTest_Repository test_Repository;
	
	@Autowired
	ReceptionReceptionRegistration_Repository registration_Repository;
	
	@Autowired
	OrganizationMaster_Repository organizationMaster_Repository;
	
	@Autowired
	MasterOtherMasters1_SubDepartment_Repository subDepartment_repository;
	
	@Autowired
	DoctorReference_Repository doctorReference_Repository;
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;
	
	@Autowired
	NumberSequenceUtilityRepository sequenceUtilityRepository;

	public BaseResponse save(ReceptionBillOpdHealthCheckup_DTO form, Long yearCd, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionBillOpdHealthCheckup_Entity entity = repository.findByBillNoAndCreditYear(form.getBillNo(), yearCd);
		if(entity !=null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Bill NO Already Exist");
			return response;
		}

		entity = new ReceptionBillOpdHealthCheckup_Entity();

		BeanUtils.copyProperties(form, entity);

		/*
		 * Other Modification On Entity
		 */
		// For Bill Number
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("HealthCheckup Bill");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();

		if(form.getBillNo().equals(prefix+""+nextSequence+""+suffix)) {
			utility.setSequence(nextSequence+1);
			sequenceUtilityRepository.save(utility);
			entity.setBillNo(prefix+""+nextSequence+""+suffix);
		}else {
			entity.setBillNo(form.getBillNo());
		}

		entity.setUserName(principal.getName());

		entity = repository.save(entity);

		for (ReceptionBillOpdHealthCheckup_Details_DTO detail : form.getDetailsList()) {
			ReceptionBillOpdHealthCheckup_Details_Entity details_Entity = new ReceptionBillOpdHealthCheckup_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
			if (detail.getProcedureDoctor1() != null)
				details_Entity.setProcedureDoctor1(
						consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
			details_Entity
					.setParticulars(test_Repository.findById(Long.valueOf(detail.getParticulars())).getTestName());
			details_Entity.setTestCode(detail.getParticulars());
			details_Entity.setBillNo(String.valueOf(entity.getId()));
			details_Repository.save(details_Entity);
		}

		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			response.setBody(entity.getId());
			return response;
		}
		return response;
	}

	public BaseResponse update(ReceptionBillOpdHealthCheckup_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionBillOpdHealthCheckup_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);

		for (ReceptionBillOpdHealthCheckup_Details_DTO detail : form.getDetailsList()) {
			ReceptionBillOpdHealthCheckup_Details_Entity details_Entity = details_Repository.findById(detail.getId());
			if (details_Entity != null) {
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity
						.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
				if (detail.getProcedureDoctor1() != null)
					details_Entity.setProcedureDoctor1(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
				details_Entity
						.setParticulars(test_Repository.findById(Long.valueOf(detail.getParticulars())).getTestName());
				details_Entity.setTestCode(detail.getParticulars());
				details_Entity.setBillNo(String.valueOf(entity.getId()));
				details_Repository.save(details_Entity);
			} else {
				details_Entity = new ReceptionBillOpdHealthCheckup_Details_Entity();
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity
						.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
				if (detail.getProcedureDoctor1() != null)
					details_Entity.setProcedureDoctor1(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
				details_Entity
						.setParticulars(test_Repository.findById(Long.valueOf(detail.getParticulars())).getTestName());
				details_Entity.setTestCode(detail.getParticulars());
				details_Entity.setBillNo(String.valueOf(entity.getId()));
				details_Repository.save(details_Entity);
			}

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

	public BaseResponse delete(long id, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		ReceptionBillOpdHealthCheckup_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<ReceptionBillOpdHealthCheckup_Details_Entity> delete = details_Repository
					.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
			details_Repository.deleteAll(delete);

			repository.delete(entity);

			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Deleted");
			return response;
		}
	}

	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal,Long creditYear) {
		List<Object[]> results = repository.getAll(creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bill", objects[0]);
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
	public BaseResponse getCurrent(String Date, Principal principal,Long creditYear) {
		List<Object[]> results = repository.getFullByDate(Date,creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bill", objects[0]);
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

	public BaseResponse getDetailsById(Long id, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");

		ReceptionBillOpdHealthCheckup_DTO dto = new ReceptionBillOpdHealthCheckup_DTO();
		List<ReceptionBillOpdHealthCheckup_Details_DTO> detailsList = new ArrayList<>();

		ReceptionBillOpdHealthCheckup_Entity entity = repository.findById(id);
		BeanUtils.copyProperties(entity, dto);

		List<ReceptionBillOpdHealthCheckup_Details_Entity> detail = details_Repository
				.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
		for (ReceptionBillOpdHealthCheckup_Details_Entity detail_temp : detail) {
			ReceptionBillOpdHealthCheckup_Details_DTO temp = new ReceptionBillOpdHealthCheckup_Details_DTO();
			BeanUtils.copyProperties(detail_temp, temp);
			detailsList.add(temp);
		}

		dto.setDetailsList(detailsList);
		response.setBody(dto);
		return response;
	}

	public BaseResponse deleteDetailById(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		ReceptionBillOpdHealthCheckup_Details_Entity entity = details_Repository.findById(id);
		details_Repository.delete(entity);
		response.setBody("Deleted");
		return response;
	}

	public BaseResponse getDetailsByBillNo(String billNo, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		ReceptionBillOpdHealthCheckup_DTO dto = new ReceptionBillOpdHealthCheckup_DTO();
		List<ReceptionBillOpdHealthCheckup_Details_DTO> detailsList = new ArrayList<>();
		ReceptionBillOpdHealthCheckup_Entity entity = repository.findByBillNoAndCreditYear(billNo,creditYear);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			BeanUtils.copyProperties(entity, dto);
			List<ReceptionBillOpdHealthCheckup_Details_Entity> detail = details_Repository
					.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
			for (ReceptionBillOpdHealthCheckup_Details_Entity detail_temp : detail) {
				ReceptionBillOpdHealthCheckup_Details_DTO temp = new ReceptionBillOpdHealthCheckup_Details_DTO();
				BeanUtils.copyProperties(detail_temp, temp);
				detailsList.add(temp);
			}
			dto.setName(registration_Repository.findByUHID(entity.getUhid()).getName());
			dto.setDetailsList(detailsList);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("List");
			response.setBody(dto);
			
		}
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse filterUhidNameBillNo(String searchtext, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.filterUhidNameBillNo(searchtext,creditYear));
		return response;
	}
	
	public byte[] report_PDF(String uhid, String format,Long creditYear) {
		try {
			String templetSrc = env.getProperty("bill.Reception_Bill.report.file");
		
			ReceptionBillOpdHealthCheckup_Entity dataList = repository.findById(Long.valueOf(uhid));
			ReceptionReceptionRegistration_Entity registration= registration_Repository.findByUHID(dataList.getUhid());
			List<ReceptionBillOpdHealthCheckup_Details_Entity> tempList = details_Repository.findByBillNoAndCreditYear(String.valueOf(dataList.getId()),creditYear);
			
			List<ReceptionBillMap> fieldList = tempList.stream().map(m-> 
			new ReceptionBillMap(
								m.getSno(),
								m.getParticulars(), 
								m.getProcedureDoctor1()!=null?m.getProcedureDoctor1():"",
								m.getRate(), 
								m.getQty(), 
								m.getAmount())
							).collect(Collectors.toList());
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("billType", "OPD HEALTHCHECKUP BILL");
			parameters.put("patientName", registration.getName());
			parameters.put("patientType", dataList.getPatientTypeOldNew());
			parameters.put("age", registration.getAge()+"/"+registration.getSex());
			parameters.put("address", registration.getAddress()+", "+registration.getCity());
			parameters.put("subDept", subDepartment_repository.findById(Long.valueOf(dataList.getSubDept())).getSubDept()); //Number
			parameters.put("organization", organizationMaster_Repository.findById(Long.valueOf(dataList.getOrganization())).getOrganization());
			parameters.put("consultant", consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1()))!=null?consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1())).getName():"");
			parameters.put("uhid", dataList.getUhid());
			parameters.put("billNo", dataList.getBillNo());
			parameters.put("mobile", registration.getMobile());
			parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(registration.getDate()));

			DoctorReference_Entity  doctorReference_Entity = !dataList.getRefBy1().isEmpty()? doctorReference_Repository.findById(Long.valueOf(dataList.getRefBy1())) : null;
			parameters.put("ref", doctorReference_Entity !=null? doctorReference_Entity.getName() : "");
			
			parameters.put("total", dataList.getTotal());
			parameters.put("paid", dataList.getPaidAmount());
			parameters.put("words", "Received with thanks a sum of Rs. "+dataList.getPaidAmount()+"/- Rupees From "+registration.getName()+" In "+dataList.getMethodOfPayment());
			if(dataList.getMethodOfPayment().equals("PLASTICMONEY")) {
				parameters.put("modeOfPayment", dataList.getMethodOfPayment()+" ("+dataList.getDetails()+")");
			}else {
				parameters.put("modeOfPayment", dataList.getMethodOfPayment());
			}
			parameters.put("userName", dataList.getUserName());
			
			return factory.getPdfFormat(templetSrc, format, parameters, fieldList);
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
		
		List<Object[]> results = repository.getFullById(Long.valueOf(searchtext),creditYear);
		ArrayList<Object> array=new ArrayList<Object>();
		for (Object[] objects : results) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bill", objects[0]);
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
