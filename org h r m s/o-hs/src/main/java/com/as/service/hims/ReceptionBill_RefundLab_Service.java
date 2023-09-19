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
import com.as.h_dto.hims.ReceptionBill_RefundLab_DTO;
import com.as.h_dto.hims.ReceptionBill_RefundLab_Details_DTO;
import com.as.h_entities.hims.ReceptionBill_RefundLab_Details_Entity;
import com.as.h_entities.hims.ReceptionBill_RefundLab_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.DoctorReference_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_SubDepartment_Repository;
import com.as.reporsitories.hims.MasterTestMaster_OpdTest_Repository;
import com.as.reporsitories.hims.OrganizationMaster_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundLab_Details_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundLab_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.ReceptionBillMap;
import com.as.response.ResponseType;
@Service
public class ReceptionBill_RefundLab_Service {

	@Autowired
	ReceptionBill_RefundLab_Details_Repository details_Repository;

	@Autowired
	ReceptionBill_RefundLab_Repository repository;

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

	public BaseResponse save(ReceptionBill_RefundLab_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionBill_RefundLab_Entity entity = null;

		entity = new ReceptionBill_RefundLab_Entity();

		BeanUtils.copyProperties(form, entity);

		/*
		 * Other Modification On Entity
		 */
		// For Bill Number
		
		entity.setLabBillNo(form.getBillNo());
		
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("Lab Bill Refund");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();
		utility.setSequence(nextSequence+1);
		sequenceUtilityRepository.save(utility);

		entity.setBillNo(prefix+""+nextSequence+""+suffix);

		entity.setUserName(principal.getName());

		entity = repository.save(entity);

		for (ReceptionBill_RefundLab_Details_DTO detail : form.getDetailsList()) {
			ReceptionBill_RefundLab_Details_Entity details_Entity = new ReceptionBill_RefundLab_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
			if (detail.getProcedureDoctor1() != null)
				details_Entity.setProcedureDoctor1(consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
			if (detail.getProcedureDoctor2() != null)
				details_Entity.setProcedureDoctor2(consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor2())).getName());
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
			return response;
		}
		return response;
	}

	public BaseResponse update(ReceptionBill_RefundLab_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionBill_RefundLab_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);

		for (ReceptionBill_RefundLab_Details_DTO detail : form.getDetailsList()) {
			ReceptionBill_RefundLab_Details_Entity details_Entity = details_Repository.findById(detail.getId());
			if (details_Entity != null) {
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity
						.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
				if (detail.getProcedureDoctor1() != null)
					details_Entity.setProcedureDoctor1(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
				if (detail.getProcedureDoctor2() != null)
					details_Entity.setProcedureDoctor2(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor2())).getName());
				details_Entity
						.setParticulars(test_Repository.findById(Long.valueOf(detail.getParticulars())).getTestName());
				details_Entity.setTestCode(detail.getParticulars());
				details_Entity.setBillNo(String.valueOf(entity.getId()));
				details_Repository.save(details_Entity);
			} else {
				details_Entity = new ReceptionBill_RefundLab_Details_Entity();
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity
						.setGroupName(group_Repository.findById(Long.valueOf(detail.getGroupName())).getGroupName());
				if (detail.getProcedureDoctor1() != null)
					details_Entity.setProcedureDoctor1(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor1())).getName());
				if (detail.getProcedureDoctor2() != null)
					details_Entity.setProcedureDoctor2(
							consultantMaster_Repository.findById(Long.valueOf(detail.getProcedureDoctor2())).getName());
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
		ReceptionBill_RefundLab_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<ReceptionBill_RefundLab_Details_Entity> delete = details_Repository.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
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

		ReceptionBill_RefundLab_DTO dto = new ReceptionBill_RefundLab_DTO();
		List<ReceptionBill_RefundLab_Details_DTO> detailsList = new ArrayList<>();

		ReceptionBill_RefundLab_Entity entity = repository.findById(id);
		BeanUtils.copyProperties(entity, dto);

		List<ReceptionBill_RefundLab_Details_Entity> detail = details_Repository.findByBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
		for (ReceptionBill_RefundLab_Details_Entity detail_temp : detail) {
			ReceptionBill_RefundLab_Details_DTO temp = new ReceptionBill_RefundLab_Details_DTO();
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
		ReceptionBill_RefundLab_Details_Entity entity = details_Repository.findById(id);
		details_Repository.delete(entity);
		response.setBody("Deleted");
		return response;
	}
	
	public byte[] report_PDF(String uhid, String format, Long creditYear) {
		try {
			String templetSrc = env.getProperty("bill.Reception_Refund.report.file");
		
			ReceptionBill_RefundLab_Entity dataList = repository.findById(Long.valueOf(uhid));
			ReceptionReceptionRegistration_Entity registration= registration_Repository.findByUHID(dataList.getUhid());
			List<ReceptionBill_RefundLab_Details_Entity> tempList = details_Repository.findByBillNoAndCreditYear(String.valueOf(dataList.getId()),creditYear);
			
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
			parameters.put("billType", "Financial Credit Note LAB");
			parameters.put("patientName", registration.getName());
			parameters.put("patientType", dataList.getPatientTypeOldNew());
			parameters.put("age", registration.getAge()+"/"+registration.getSex());
			parameters.put("address", registration.getAddress()+", "+registration.getCity());
			parameters.put("subDept", subDepartment_repository.findById(Long.valueOf(dataList.getSubDept())).getSubDept()); //Number
			parameters.put("organization", organizationMaster_Repository.findById(Long.valueOf(dataList.getOrganization())).getOrganization());
			parameters.put("consultant", consultantMaster_Repository.findById(Long.valueOf(dataList.getConsultant1())).getName());
			parameters.put("ref", doctorReference_Repository.findById(Long.valueOf(dataList.getRefBy1()))!=null?doctorReference_Repository.findById(Long.valueOf(dataList.getRefBy1())).getName():"");
			parameters.put("uhid", dataList.getUhid());
			parameters.put("billNo", dataList.getBillNo());
			parameters.put("mobile", registration.getMobile());
			parameters.put("date", registration.getDate());
			parameters.put("total", dataList.getTotal());
			parameters.put("paid", dataList.getPaidAmount());
			parameters.put("words", "Received with thanks a sum of Rs. "+dataList.getPaidAmount()+"/- Rupees From "+registration.getName()+" In "+dataList.getMethodOfPayment());
			parameters.put("modeOfPayment", dataList.getMethodOfPayment());
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
