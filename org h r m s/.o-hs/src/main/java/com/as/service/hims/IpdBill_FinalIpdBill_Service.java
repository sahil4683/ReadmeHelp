package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.as.h_dto.hims.Discharge_DTO;
import com.as.h_dto.hims.IpdBill_FinalIpdBill_DTO;
import com.as.h_dto.hims.IpdBill_FinalIpdBill_Details_DTO;
import com.as.h_entities.hims.IpdBill_FinalIpdBill_Details_Entity;
import com.as.h_entities.hims.IpdBill_FinalIpdBill_Entity;
import com.as.h_entities.hims.MasterTestMaster_IpdTest_Entity;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.h_entities.hims.RequestSheetIpd_Details_Entity;
import com.as.h_entities.hims.RequestSheetIpd_Entity;
import com.as.reporsitories.NumberSequenceUtilityRepository;
import com.as.reporsitories.hims.IpdBill_FinalIpdBill_Details_Repository;
import com.as.reporsitories.hims.IpdBill_FinalIpdBill_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.reporsitories.hims.MasterOtherMasters2_ClassMaster_Repository;
import com.as.reporsitories.hims.MasterTestMaster_IpdTest_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reporsitories.hims.RequestSheetIpd_Details_Repository;
import com.as.reporsitories.hims.RequestSheetIpd_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.AdmitedPatientListForShiftingResponse;
import com.as.response.BaseResponse;
import com.as.response.IpdBillResponse;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;
import com.as.utils.TimeFormatChanger;

@Service
public class IpdBill_FinalIpdBill_Service {

	@Autowired
	IpdBill_FinalIpdBill_Details_Repository details_Repository;

	@Autowired
	IpdBill_FinalIpdBill_Repository repository;
	
	
	@Autowired
	RequestSheetIpd_Repository requestSheetIpd_Repository;
	
	@Autowired
	RequestSheetIpd_Details_Repository requestSheetIpd_Details_Repository;
	
	@Autowired
	MasterTestMaster_IpdTest_Repository test_Repository;
	
	@Autowired
	ReceptionReceptionAdmission_Repository admission_Repository;
	
	@Autowired
	ReceptionReceptionRegistration_Repository registration_repository;
	
	@Autowired
	MasterOtherMasters1_Group_Repository group_repository;
	
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;
	
	@Autowired
	NumberSequenceUtilityRepository sequenceUtilityRepository;
	
	@Autowired
	MasterOtherMasters2_ClassMaster_Repository classMaster_Repository;
	
	@Autowired
	DischargeService dischargeService;
	

	public BaseResponse save(IpdBill_FinalIpdBill_DTO form, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		IpdBill_FinalIpdBill_Entity entity = repository.findByIpdnoAndCreditYear(form.getIpdno(),creditYear);
		if (entity != null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Already Exits");
			return response;
		}
		entity = new IpdBill_FinalIpdBill_Entity();
		BeanUtils.copyProperties(form, entity);

		/*
		 * Other Modification On Entity
		 */
		
		NumberSequenceUtility utility = sequenceUtilityRepository.findByName("IPD Bill");
		Long nextSequence = utility.getSequence() == null ? 1 : utility.getSequence();
		String prefix = utility.getPrefix() == null ? "" : utility.getPrefix();
		String suffix = utility.getSuffix() == null ? "" : utility.getSuffix();
		utility.setSequence(nextSequence+1);
		sequenceUtilityRepository.save(utility);
		
		entity.setBillNo(prefix+""+nextSequence+""+suffix);

		entity.setUserName(principal.getName());
		entity.setReadyForDischarge(true);
		entity = repository.save(entity);

		for (IpdBill_FinalIpdBill_Details_DTO detail : form.getDetails()) {
			IpdBill_FinalIpdBill_Details_Entity details_Entity = new IpdBill_FinalIpdBill_Details_Entity();
			BeanUtils.copyProperties(detail, details_Entity);
			details_Entity.setIpdBillNo(String.valueOf(entity.getId()));
			if(detail.getTestCode() == null) { details_Entity.setTestCode(detail.getTestId()); }
			details_Repository.save(details_Entity);
		}

		Discharge_DTO discharge_DTO = getdischargeDTO(entity, creditYear);
		dischargeService.saveOrUpdate(discharge_DTO, principal, creditYear);

		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			response.setBody(entity.getId());
			return response;
		}
		return response;
	}
	
	private Discharge_DTO getdischargeDTO(IpdBill_FinalIpdBill_Entity entity, Long creditYear) {
		Discharge_DTO discharge_DTO = new Discharge_DTO();
		discharge_DTO.setDate(entity.getDischargeDate());
		discharge_DTO.setDtime(entity.getDischargeTime());
		discharge_DTO.setPname(registration_repository.findByUHID(entity.getUhid()).getName());
		discharge_DTO.setIpdno(entity.getIpdno());
		discharge_DTO.setCaseno(entity.getUhid());
		discharge_DTO.setBedNo(admission_Repository.findByIPDNOAndCreditYear(entity.getIpdno(), creditYear).getBEDNO());
		discharge_DTO.setDtype(entity.getDischargeType());
		if (entity.getDischargeType().equals("Date Of Discharge")) {
			discharge_DTO.setDtype("DISCHARGE");
		}
		if (entity.getDischargeType().equals("Date Of Expired")) {
			discharge_DTO.setDtype("EXPIRED");
		}
		if (entity.getDischargeType().equals("Date Of Discharge on Request")) {
			discharge_DTO.setDtype("DISCHARGE ON REQUEST");
		}
		if (entity.getDischargeType().equals("Date Of Discharge on Improved")) {
			discharge_DTO.setDtype("IMPROVED");
		}
		if (entity.getDischargeType().equals("Date Of Discharge on Transfer")) {
			discharge_DTO.setDtype("TRANSFER");
		}
		if (entity.getDischargeType().equals("Without Permission")) {
			discharge_DTO.setDtype("WITHOUT PERMISSION");
		}
		if (entity.getDischargeType().equals("Absconding")) {
			discharge_DTO.setDtype("ABSCONDING");
		}
		if (entity.getDischargeType().equals("Date Of Dama")) {
			discharge_DTO.setDtype("DAMA");
		}
		return discharge_DTO;
	}

	public BaseResponse update(IpdBill_FinalIpdBill_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		IpdBill_FinalIpdBill_Entity entity = repository.findById(form.getId());
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		BeanUtils.copyProperties(form, entity);

		for (IpdBill_FinalIpdBill_Details_DTO detail : form.getDetails()) {
			IpdBill_FinalIpdBill_Details_Entity details_Entity = details_Repository.findById(detail.getId());
			if (details_Entity != null) {
				BeanUtils.copyProperties(detail, details_Entity);
				details_Repository.save(details_Entity);
			} else {
				details_Entity = new IpdBill_FinalIpdBill_Details_Entity();
				BeanUtils.copyProperties(detail, details_Entity);
				details_Entity.setIpdBillNo(String.valueOf(form.getId()));
				details_Repository.save(details_Entity);
			}

		}

//		entity.setReadyForDischarge(true);
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
		IpdBill_FinalIpdBill_Entity entity = repository.findById(id);
		if (entity == null) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		} else {
			List<IpdBill_FinalIpdBill_Details_Entity> delete = details_Repository.findByIpdBillNoAndCreditYear(String.valueOf(entity.getId()),creditYear);
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
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByAllTable(creditYear));
//		response.setBody(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
		
		return response;
	}

	public BaseResponse getDetailsById(String id, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(details_Repository.findByIpdBillNoAndCreditYear(id,creditYear));
		return response;
	}

	public BaseResponse deleteDetailById(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		IpdBill_FinalIpdBill_Details_Entity entity = details_Repository.findById(id);
		details_Repository.delete(entity);
		response.setBody("Deleted");
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getCurrent(String Date, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByAllTableByDate(Date,creditYear));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getLabRequest(String ipdno,String type, Principal principal,Long creditYear) {

		List<RequestSheetIpd_Entity> requestList=requestSheetIpd_Repository.findByIpdnoAndTypeAndCreditYear(ipdno,type,creditYear);
		if(requestList.isEmpty()) {
			BaseResponse response = new BaseResponse();
			response.setStatus(400);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Test Details Found !");
			return response;
		}
		ReceptionReceptionAdmission_Entity admission=admission_Repository.findByIPDNOAndCreditYear(ipdno,creditYear);
		List<MasterTestMaster_IpdTest_Entity> testList=test_Repository.findAll();
		List<LabRequestResponse> labResponse = new ArrayList<LabRequestResponse>();
		for(RequestSheetIpd_Entity request:requestList) {
			List<RequestSheetIpd_Details_Entity> requestDetail=requestSheetIpd_Details_Repository.findByRequestIdAndCreditYear(String.valueOf(request.getId()),creditYear);
			labResponse.addAll(requestDetail.stream().map(m-> new LabRequestResponse(
					m.getId(),
					m.getSno(), 
					m.getTestName(), 
					m.getTestId(), 
					"", 
					"", 
					"1",
					getClassRate(admission,Long.valueOf(m.getTestId()),testList), 
					"", 
					"", 
					getClassRate(admission,Long.valueOf(m.getTestId()),testList), 
					"", 
					"", 
					request.getDate(),
					m.getTime(),
					request.getType(),
					request.getOrganization(),
					String.valueOf(classMaster_Repository.findByClassName(admission.getWardname()).getId()),
					test_Repository.findById(Long.valueOf(m.getTestId())).getGroupName(),
					group_repository.findById(Long.valueOf(test_Repository.findById(Long.valueOf(m.getTestId())).getGroupName())).getGroupName(),
					test_Repository.findById(Long.valueOf(m.getTestId())).getSuperGroup()
					)
					).collect(Collectors.toList()));
		}

		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(labResponse);
		return response;
	}

	
	public String getClassRate(ReceptionReceptionAdmission_Entity admission,
			Long testId,List<MasterTestMaster_IpdTest_Entity> testList) {
		String className=admission.getWardname();
		if (className.equals("GENERAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
			.map(MasterTestMaster_IpdTest_Entity::getGeneral).findAny().orElse(null);
		}
		if (className.equals("NICU")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
			.map(MasterTestMaster_IpdTest_Entity::getNicu).findAny().orElse(null);
		}
		if (className.equals("SEMI SPECIAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getSemiSpecial).findAny().orElse(null);
			}
		if (className.equals("SPECIAL")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getSpecial).findAny().orElse(null);
			}
		if (className.equals("DELUXE")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getDeluxe).findAny().orElse(null);
			}
		if (className.equals("EXECUTIVE")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getExecutive).findAny().orElse(null);
			}
		if (className.equals("ICU")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getIcu).findAny().orElse(null);
			}
		if (className.equals("EMERGENCY")) { return testList.stream().filter(x ->  x.getId().equals(testId) )
				.map(MasterTestMaster_IpdTest_Entity::getEmergency).findAny().orElse(null);
			}
		//Write Same For All
		return null;
	}

	public byte[] report_PDF(String id, String format,String type,Principal principal,Long creditYear) {
		try {			
			String templetSrc = "";

			IpdBill_FinalIpdBill_Entity billList = repository.findById(Long.valueOf(id));

			List<IpdBill_FinalIpdBill_Details_Entity> tesmList = details_Repository.findByIpdBillNoAndCreditYear(String.valueOf(billList.getId()),creditYear);
			ReceptionReceptionRegistration_Entity registration = registration_repository.findByUHID(billList.getUhid());

			List<IpdBillResponse> MainList = null; 
			Map<String, Object> parameters = new HashMap<>();
			if(tesmList.isEmpty()) {
				tesmList = new ArrayList<IpdBill_FinalIpdBill_Details_Entity>();
			}
			
			if(type.equals("bill")) {
				templetSrc = env.getProperty("ipd.bill.file");
				parameters.put("billTitle", "IPD BILL");
				MainList=tesmList.stream().map(m->
				new IpdBillResponse(
						m.getSno(), 
						(m.getGroupCode()==null) ?m.getGroupName(): group_repository.findById(Long.valueOf(m.getGroupCode())).getGroupName(),
						m.getAmount()
						)).collect(Collectors.toList());
				
				//ReCreted For Group And Sum Amount
				MainList=MainList.stream().collect(Collectors.groupingBy(foo -> foo.getDescription()))
	            .entrySet().stream()
	            .map(e -> e.getValue().stream()
	                .reduce((f1,f2) -> new IpdBillResponse(
	                		f1.getSno(),
	                		f1.getDescription(),
	                		String.valueOf(Integer.valueOf(f1.getAmount()) + Integer.valueOf(f2.getAmount())))
	                		))
	                .map(f -> f.get())
	                .sorted(Comparator.comparing(IpdBillResponse::getDescription))
	                .collect(Collectors.toList());	
			}
			if(type.equals("detail")) {
				templetSrc = env.getProperty("ipd.bill_Detail.file");
				parameters.put("billTitle", "IPD BILL DETAIL");
				MainList=tesmList.stream().map(m->
				new IpdBillResponse(
						m.getSno(), 
						(m.getGroupCode()==null) ?m.getGroupName(): group_repository.findById(Long.valueOf(m.getGroupCode())).getGroupName(),
						m.getAmount(),
						m.getTestName(),
						m.getProcedureDoctor1()!=null?m.getProcedureDoctor1():" ",
						m.getRate(),
						m.getQty(),
						billList.getDate()
						)).sorted(Comparator.comparing(IpdBillResponse::getDescription)).collect(Collectors.toList());
				
				
			}
			if(type.equals("date")) {
				templetSrc = env.getProperty("ipd.bill_Detail_Date.file");
				parameters.put("billTitle", "IPD BILL DETAIL");
				MainList=tesmList.stream().map(m->
				new IpdBillResponse(
						m.getSno(), 
						group_repository.findById(Long.valueOf(m.getGroupCode())).getGroupName(),
						m.getAmount(),
						m.getTestName(),
						m.getProcedureDoctor1()!=null?m.getProcedureDoctor1():" ",
						m.getRate(),
						m.getQty(),
						billList.getDate()
						)).collect(Collectors.toList());
			}
			
			parameters.put("billNo", billList.getBillNo());
			parameters.put("uhid", billList.getUhid());
			parameters.put("patientName", registration.getName());
			parameters.put("address", registration.getAddress()+","+registration.getCity()+","+registration.getState());
			parameters.put("consultant", billList.getConsultant1());
			parameters.put("admissionDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(billList.getAdmissionDate())+" "+TimeFormatChanger.Time24HourTo12Hour(billList.getAdmissionTime()));
			parameters.put("creditAgainst", billList.getCreditAgainst());
			parameters.put("date", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(billList.getDate()));
			parameters.put("ipdno", billList.getIpdno());
			parameters.put("age", billList.getAge()+" / "+billList.getSex());
			parameters.put("mobile", registration.getMobile());
			parameters.put("dischargeDate", DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(billList.getDischargeDate())+" "+TimeFormatChanger.Time24HourTo12Hour(billList.getDischargeTime()));
			parameters.put("userName", billList.getUserName());
			
			parameters.put("total", billList.getTotal());
			int gross = Integer.valueOf(billList.getTotal()!=null?billList.getTotal():"0") + Integer.valueOf(billList.getServiceAmt()!=null?billList.getServiceAmt():"0");
			parameters.put("grossTotal", String.valueOf(gross));
			parameters.put("netPayableAmount", billList.getNetTotal());
			parameters.put("netTotalBillAmount", billList.getGrandTotal());
			
			parameters.put("serviceName", billList.getServiceName());
			parameters.put("servicePer", billList.getServicePer());
			parameters.put("serviceAmt", billList.getServiceAmt());
			
			parameters.put("deposit", billList.getLessDeposit());
			
			
			return factory.getPdfFormat(templetSrc, format, parameters, MainList);
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
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByAllTableById(searchtext,creditYear));
		return response;
	}
	
	
	public BaseResponse getReadyForDischarge(Long creditYear) {
		BaseResponse baseResponse = new BaseResponse();
		List<AdmitedPatientListForShiftingResponse> patienDetailsList = repository.getReadyForDischarge("1",creditYear);
		if (patienDetailsList != null && patienDetailsList.size() > 0) {
			baseResponse.setBody(patienDetailsList);
			baseResponse.setMessage("Record found Successfully");
			baseResponse.setStatus(200);
		} else {
			baseResponse.setMessage("Record not found");
			baseResponse.setStatus(404);
		}
		return baseResponse;
	}
	
}




