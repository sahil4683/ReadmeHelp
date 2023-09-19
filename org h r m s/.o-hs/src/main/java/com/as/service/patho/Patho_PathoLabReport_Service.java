package com.as.service.patho;

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

import com.as.h_dto.patho.Patho_EnterData_DTO;
import com.as.h_entities.hims.ConsultantMaster_Entity;
import com.as.h_entities.hims.ReceptionBillLab_Details_Entity;
import com.as.h_entities.hims.ReceptionBillLab_Entity;
import com.as.h_entities.hims.ReceptionReceptionAdmission_Entity;
import com.as.h_entities.hims.ReceptionReceptionRegistration_Entity;
import com.as.h_entities.hims.RequestSheetIpd_Details_Entity;
import com.as.h_entities.hims.RequestSheetIpd_Entity;
import com.as.h_entities.patho.Patho_AuditTrailPatho_Entity;
import com.as.h_entities.patho.Patho_EnterData_Entity;
import com.as.h_entities.patho.Patho_FormatGroupMaster_Entity;
import com.as.h_entities.patho.Patho_FormatMaster_Entity;
import com.as.h_entities.patho.Patho_MachineMaster_Entity;
import com.as.h_entities.patho.Patho_MapLabTest_Entity;
import com.as.h_entities.patho.Patho_ObservationMaster_Entity;
import com.as.h_entities.patho.Patho_PathoLabReport_Entity;
import com.as.h_entities.patho.Patho_SampleDeviceMaster_Entity;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.DoctorReference_Repository;
import com.as.reporsitories.hims.ReceptionBillLab_Details_Repository;
import com.as.reporsitories.hims.ReceptionBillLab_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reporsitories.hims.RequestSheetIpd_Details_Repository;
import com.as.reporsitories.hims.RequestSheetIpd_Repository;
import com.as.reporsitories.patho.Patho_AuditTrailPatho_Repository;
import com.as.reporsitories.patho.Patho_EnterData_Repository;
import com.as.reporsitories.patho.Patho_FormatGroupMaster_Repository;
import com.as.reporsitories.patho.Patho_FormatMaster_Repository;
import com.as.reporsitories.patho.Patho_MachineMaster_Repository;
import com.as.reporsitories.patho.Patho_MapLabTest_Repository;
import com.as.reporsitories.patho.Patho_ObservationMaster_Repository;
import com.as.reporsitories.patho.Patho_PathoLabReport_Repository;
import com.as.reporsitories.patho.Patho_SampleDeviceMaster_Repository;
import com.as.reports.JasperReportFactory;
import com.as.request.SaveTestDataDTO;
import com.as.response.BaseResponse;
import com.as.response.PathoWatingPatientListResponse;
import com.as.response.ResponseType;
import com.as.response.WatingPatientResponse;
import com.as.utils.DateFormatChanger;
import com.as.utils.TimeFormatChanger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class Patho_PathoLabReport_Service {
	
	@Autowired
	ReceptionReceptionRegistration_Repository registration_Repository;

	@Autowired
	ReceptionReceptionAdmission_Repository admission_Repository;

	@Autowired
	ConsultantMaster_Repository doctor_Repository;

	@Autowired
	DoctorReference_Repository doctorReference_Repository;

	@Autowired
	RequestSheetIpd_Repository requestSheetIpd_Repository;

	@Autowired
	RequestSheetIpd_Details_Repository requestSheetIpd_Details_Repository;

	@Autowired
	Patho_PathoLabReport_Repository repository;

	@Autowired
	Patho_AuditTrailPatho_Repository auditTrailPatho_Repository;

	@Autowired
	Patho_EnterData_Repository enterData_Repository;
	
	@Autowired
	ReceptionBillLab_Details_Repository lab_Details_Repository;

	@Autowired
	ReceptionBillLab_Repository lab_Repository;
	
	@Autowired
	Patho_ObservationMaster_Repository observationMaster_Repository;
	
	@Autowired
	Patho_FormatMaster_Repository formatMaster_Repository;
	
	@Autowired
	Patho_FormatGroupMaster_Repository formatGroupMaster_Repository;
	
	@Autowired
	Patho_MapLabTest_Repository mapLabTest_Repository;
	
	@Autowired
	Patho_MachineMaster_Repository machineMaster_Repository;
	
	@Autowired
	Patho_SampleDeviceMaster_Repository sampleDeviceMaster_Repository;

	@Autowired
	JasperReportFactory factory;

	@Autowired
	Environment env;
	
	/*
	 * Backup Code
	 * */
//	@ReadOnlyProperty
//	public BaseResponse getWaitingPatientByDate(String date, Principal principal) {
//		BaseResponse response = new BaseResponse();
//		response.setStatus(200);
//		response.setType(ResponseType.SUCCESS);
//		response.setMessage("List");
//		response.setBody(repository.getWatingPatientList(date));
//		return response;
//	}
	
	/*********************************************************/
	/******** Get Count Of Collected, Received, Print ********/
	/*********************************************************/
	@ReadOnlyProperty
	public BaseResponse getCountByDate(String date, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getAllCountByDate(date,creditYear));
		return response;
	}
	
	
	/*********************************************************/
	/******** Get Waiting List ********/
	/*********************************************************/
	@ReadOnlyProperty
	public BaseResponse getWaitingPatientByDate(String date, Principal principal,Long creditYear) {
		List<PathoWatingPatientListResponse> labBillTable = getLabBillTable(date,creditYear);
		List<PathoWatingPatientListResponse> requestSheetTable = getRequestSheetTable(date,creditYear);
		List<PathoWatingPatientListResponse> all=new ArrayList<>();
		all.addAll(requestSheetTable);
		all.addAll(labBillTable);
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(all);
		return response;
	}
	
	private List<PathoWatingPatientListResponse> getLabBillTable(String date, Long creditYear){
		List<PathoWatingPatientListResponse> dataList =new ArrayList<PathoWatingPatientListResponse>();
		
		List<ReceptionBillLab_Entity> labBill = lab_Repository.findByDateAndCreditYear(date,creditYear);
		for (ReceptionBillLab_Entity labEntity : labBill) {
			
			List<ReceptionReceptionAdmission_Entity> admitList = admission_Repository.findByUHIDAndCreditYearOrderByIdDesc(labEntity.getUhid(),creditYear);	
			
			if(admitList.size() != 0 && admitList.get(0) !=null) {
				ReceptionReceptionAdmission_Entity admit = admitList.get(0);
	
				PathoWatingPatientListResponse response = new PathoWatingPatientListResponse();
				response.setDept("BillLab");
				response.setIpdno(String.valueOf(admit.getIPDNO()));
				response.setDate(date);
				
				ReceptionReceptionRegistration_Entity register=registration_Repository.findByUHID(labEntity.getUhid());
				response.setName(register.getName());
				response.setUhid(String.valueOf(register.getUHID()));
				response.setSex(register.getSex());
				response.setAge(register.getAge());
				response.setMobile(register.getMobile());
				
				ConsultantMaster_Entity consultant = doctor_Repository.findById(Long.valueOf(labEntity.getConsultant1()));
				response.setConsultant(consultant.getName());
				StringBuffer nameText=new StringBuffer();
				StringBuffer idText =new StringBuffer();
				
				//Loop To Test Done
				List<ReceptionBillLab_Details_Entity>  details = lab_Details_Repository.findByBillNoAndCreditYear(String.valueOf(labEntity.getId()),creditYear);
				for (ReceptionBillLab_Details_Entity detail : details) {
					nameText.append(detail.getParticulars()+";");
					idText.append(detail.getTestCode()+";");
				}
				response.setBillNo(String.valueOf(labEntity.getId()));
				response.setTestId(idText.toString());
				response.setTestName(nameText.toString());
				dataList.add(response);
			}
		}
		/**** Backup Code If Something Wrong In Path Waiting List ***/
//		List<ReceptionReceptionAdmission_Entity> admissions=admission_Repository.findByDate(date);
//		//First Get Admission On Date
//		for (ReceptionReceptionAdmission_Entity admission : admissions) {
//			//Loop To Test By UHID/IPD
//			List<ReceptionBillLab_Entity> requests = lab_Repository.findByUhid(String.valueOf(admission.getUHID()));
//			for (ReceptionBillLab_Entity request : requests) {
//				//Set Patient Data to response		
//				PathoWatingPatientListResponse response = new PathoWatingPatientListResponse();
//				response.setDept("BillLab");
//				response.setIpdno(String.valueOf(admission.getIPDNO()));
//				response.setDate(request.getDate());
//				ReceptionReceptionRegistration_Entity register=registration_Repository.findByUHID(admission.getUHID());
//				response.setName(register.getName());
//				response.setUhid(String.valueOf(register.getUHID()));
//				response.setSex(register.getSex());
//				response.setAge(register.getAge());
//				response.setMobile(register.getMobile());
//				ConsultantMaster_Entity consultant = doctor_Repository.findById(Long.valueOf(admission.getConsultant1()));
//				response.setConsultant(consultant.getName());
//				StringBuffer nameText=new StringBuffer();
//				StringBuffer idText =new StringBuffer();
//				//Loop To Test Done
//				List<ReceptionBillLab_Details_Entity>  details = lab_Details_Repository.findByBillNoAndCreditYear(String.valueOf(request.getId()),creditYear);
//				for (ReceptionBillLab_Details_Entity detail : details) {
//					nameText.append(detail.getParticulars()+";");
//					idText.append(detail.getTestCode()+";");
//				}
//				response.setBillNo(String.valueOf(request.getId()));
//				response.setTestId(idText.toString());
//				response.setTestName(nameText.toString());
//				dataList.add(response);
//			}
//		}
		return dataList;
	}
	private List<PathoWatingPatientListResponse> getRequestSheetTable(String date,Long creditYear){
		List<PathoWatingPatientListResponse> dataList =new ArrayList<PathoWatingPatientListResponse>();
		
		List<RequestSheetIpd_Entity> requests = requestSheetIpd_Repository.findByDateAndCreditYear(date,creditYear);
		for (RequestSheetIpd_Entity request : requests) {
			boolean checkIsPresent = false;
			ReceptionReceptionAdmission_Entity admit = admission_Repository.findByIPDNOAndCreditYear(request.getIpdno(), creditYear);
			PathoWatingPatientListResponse response = new PathoWatingPatientListResponse();
			response.setDept("PathoRequest");
			response.setIpdno(String.valueOf(admit.getIPDNO()));
			response.setDate(request.getDate());
			ReceptionReceptionRegistration_Entity register=registration_Repository.findByUHID(request.getUhid());
			response.setName(register.getName());
			response.setUhid(String.valueOf(register.getUHID()));
			response.setSex(register.getSex());
			response.setAge(register.getAge());
			response.setMobile(register.getMobile());
			ConsultantMaster_Entity consultant = doctor_Repository.findById(Long.valueOf(admit.getConsultant1()));
			response.setConsultant(consultant!=null?consultant.getName():"");
			StringBuffer nameText=new StringBuffer();
			StringBuffer idText =new StringBuffer();
			List<RequestSheetIpd_Details_Entity>  details = requestSheetIpd_Details_Repository.findByRequestIdAndCreditYear(String.valueOf(request.getId()),creditYear);
			for (RequestSheetIpd_Details_Entity detail : details) {
				nameText.append(detail.getTestName()+";");
				idText.append(detail.getTestName()+";");
				checkIsPresent = true;
			}
			response.setBillNo(String.valueOf(request.getId()));
			response.setTestId(idText.toString());
			response.setTestName(nameText.toString());
			if(checkIsPresent) {
				dataList.add(response);	
			}
			
		}
		/**** Backup Code If Something Wrong In Path Waiting List ***/
//		List<ReceptionReceptionAdmission_Entity> admissions=admission_Repository.findByDate(date);
//		for (ReceptionReceptionAdmission_Entity admission : admissions) {
//			List<RequestSheetIpd_Entity> requests = requestSheetIpd_Repository.findByIpdnoAndCreditYear(String.valueOf(admission.getIPDNO()),creditYear);
//			for (RequestSheetIpd_Entity request : requests) {
//				PathoWatingPatientListResponse response = new PathoWatingPatientListResponse();
//				response.setDept("PathoRequest");
//				response.setIpdno(String.valueOf(admission.getIPDNO()));
//				response.setDate(request.getDate());
//				ReceptionReceptionRegistration_Entity register=registration_Repository.findByUHID(admission.getUHID());
//				response.setName(register.getName());
//				response.setUhid(String.valueOf(register.getUHID()));
//				response.setSex(register.getSex());
//				response.setAge(register.getAge());
//				response.setMobile(register.getMobile());
//				ConsultantMaster_Entity consultant = doctor_Repository.findById(Long.valueOf(admission.getConsultant1()));
//				response.setConsultant(consultant.getName());
//				StringBuffer nameText=new StringBuffer();
//				StringBuffer idText =new StringBuffer();
//				List<RequestSheetIpd_Details_Entity>  details = requestSheetIpd_Details_Repository.findByRequestIdAndCreditYear(String.valueOf(request.getId()),creditYear);
//				for (RequestSheetIpd_Details_Entity detail : details) {
//					nameText.append(detail.getTestName()+";");
//					idText.append(detail.getTestName()+";");
//				}
//				response.setBillNo(String.valueOf(request.getId()));
//				response.setTestId(idText.toString());
//				response.setTestName(nameText.toString());
//				dataList.add(response);
//			}
//		}
		return dataList;
	}


	/*********************************************************/
	/******** Waiting Button Click ********/
	/*********************************************************/
	public BaseResponse waitingRequest(PathoWatingPatientListResponse item, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		ReceptionReceptionAdmission_Entity admission = admission_Repository.findByIPDNOAndCreditYear(item.getIpdno(),creditYear);
		ReceptionReceptionRegistration_Entity registration = registration_Repository.findByUHID(item.getUhid());
		
		WatingPatientResponse test = null;
		List<WatingPatientResponse> testList = null;
		
		if(item.getDept().equals("PathoRequest")) {
			test = repository.getWatingPatientTestRequestSheet(Long.valueOf(item.getUhid()), Long.valueOf(item.getIpdno()), item.getDate(), item.getBillNo());
			testList = repository.getWatingPatientTestArrayRequestSheet(Long.valueOf(item.getUhid()), Long.valueOf(item.getIpdno()), item.getDate(), item.getBillNo(),creditYear);
		}else {
			test = repository.getWatingPatientTestLabBill(Long.valueOf(item.getUhid()), item.getDate(), item.getBillNo());
			testList = repository.getWatingPatientTestArrayLabBill(Long.valueOf(item.getUhid()), item.getDate(), item.getBillNo(),creditYear);
		}
		
		// Save Patient Master (registration, admission, testArray Of String to Save)
		Patho_PathoLabReport_Entity patientMasterData = setWaitingData(registration, admission, test);
		patientMasterData = repository.save(patientMasterData);

		if (patientMasterData.getId() != 0) {
			final String id = String.valueOf(patientMasterData.getId());
			//For Pop UP Test List
			List<AddIdToTestList> testListPopup = testList.stream().map(m -> new AddIdToTestList(String.valueOf(id), m.getUhid(),
					m.getIpdno(), m.getTestId(), m.getTestName(), item.getDate(),item.getDept())).collect(Collectors.toList());
			
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("TestName");
			response.setBody(testListPopup);
			return response;
		}
		response.setStatus(300);
		response.setType(ResponseType.WARNING);
		response.setMessage("Not Found");
		return response;
	}

	private Patho_PathoLabReport_Entity setWaitingData(ReceptionReceptionRegistration_Entity registration,
			ReceptionReceptionAdmission_Entity admission, WatingPatientResponse test) {
		Patho_PathoLabReport_Entity report = new Patho_PathoLabReport_Entity();
		Long nextSrno = repository.getNextsrno();
		nextSrno = (nextSrno == null ? 1 : nextSrno);
		report.setSrno(String.valueOf(nextSrno)); // Ckeck ❌
		report.setDate(DateFormatChanger.getCurrentDate());
		report.setPatientId(String.valueOf(admission.getId()));
		report.setPatientName(registration.getName());
		report.setUhid(String.valueOf(admission.getUHID()));
		report.setIpdno(String.valueOf(admission.getIPDNO()));
		report.setDob(registration.getDOB());
		report.setGender(registration.getSex());
		report.setAge(registration.getAge());
		report.setMobile(registration.getMobile());
		report.setPatientType("CAMP PATIENT");
		report.setReferredBy(registration.getReferredBy() != 0
				? doctorReference_Repository.findById(Long.valueOf(registration.getReferredBy())).getName()
				: null);
		
		report.setDeptId("0"); // Check ❌
		report.setDept(test.getDept()); // Check ❌ -> For OPD-LAB = 'BillLab' AND For IPD-Request = 'PathoRequest'
		report.setBillNo(test.getBillNo());
		report.setTestId(test.getTestId());
		report.setTestName(test.getTestName());
		report.setTimeOfCollection(TimeFormatChanger.getCurrentTime());
		report.setTimeOFReceived(TimeFormatChanger.getCurrentTime());
		report.setSampleReceived(TimeFormatChanger.getCurrentTime());
//		private String specimen;
//		private String flag;
//		private String status;
//		private String labName; //N
//		private String entredBy;
//		private String entredByDesignation;
//		private String verifyBy;
//		private String verifyByDesignation;
		report.setWaitingFlag(true); // Check X
		report.setCollectedFlag(true);
		report.setReceivedFlag(false);
		report.setPrintedFlag(false);
//		private String companyId;
//		private String subLocationId;
//		private String activityId; //N
//		private String reportTime;
//		private String reviseTime;
//		private String reviseDate;
//		private String imp_eflag;
		return report;
	}

	
	/*********************************************************/
	/******** Test Name Popup Save Click ********/
	/*********************************************************/
	public BaseResponse saveTestData(List<SaveTestDataDTO> form, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();

		int sizeOfTestForm = form.size();
		int sizeOfTestDB = 0;

		if(form.get(0).getDept().equals("PathoRequest")) {
			sizeOfTestDB = repository.getWatingPatientTestArrayRequestSheet(
									Long.valueOf(form.get(0).getUhid()), 
									Long.valueOf(form.get(0).getIpdno()),
									form.get(0).getDate(),
									form.get(0).getBillNo(),
									creditYear
								).size();
		}else {
			sizeOfTestDB = repository.getWatingPatientTestArrayLabBill(
									Long.valueOf(form.get(0).getUhid()),
									form.get(0).getDate(),
									form.get(0).getBillNo(),
									creditYear
								).size();
		}
		
		if (sizeOfTestForm == sizeOfTestDB) {
			Patho_AuditTrailPatho_Entity entity = new Patho_AuditTrailPatho_Entity(
					form.get(0).getId(), 
					"AddTestName",
					"ALL Test", 
					principal.getName(), 
					"Add", 
					DateFormatChanger.getCurrentDate(), 
					null,
					repository.findById(Long.valueOf(form.get(0).getId())).getPatientName()
					);
			entity = auditTrailPatho_Repository.save(entity);
			
			saveTestIntoEnterData(form,principal);
			
			if (entity.getId() != 0) {
				response.setStatus(200);
				response.setType(ResponseType.SUCCESS);
				response.setMessage("Saved");
				return response;
			}
		} else {
			List<Patho_AuditTrailPatho_Entity> enList = form.stream().map(m -> 
					new Patho_AuditTrailPatho_Entity(
							m.getId(), 
							"AddTestName", 
							mapLabTest_Repository.findByOldTest(m.getTestName()) != null ? mapLabTest_Repository.findByOldTest(m.getTestName()).getNewTest() : null,
							principal.getName(), 
							"Add", 
							DateFormatChanger.getCurrentDate(), 
							null,
							repository.findById(Long.valueOf(m.getId())).getPatientName()
						)
					).collect(Collectors.toList());
			
			enList = auditTrailPatho_Repository.saveAll(enList);
			
			String testNames = form.stream().map(m -> m.getTestName()).collect(Collectors.joining(";"));
			String testIds = form.stream().map(m -> m.getTestId()).collect(Collectors.joining(";"));
			Patho_PathoLabReport_Entity labReport = repository.findById(Long.valueOf(form.get(0).getId()));
			labReport.setTestName(testNames);
			labReport.setTestId(testIds);
			repository.save(labReport);
			
			saveTestIntoEnterData(form,principal);
			
			if (enList.size() != 0) {
				response.setStatus(200);
				response.setType(ResponseType.SUCCESS);
				response.setMessage("Saved");
				return response;
			}
		}
		return response;
	}
	
	public BaseResponse saveTestIntoEnterData(List<SaveTestDataDTO> form, Principal principal) {
		BaseResponse response = new BaseResponse();
		List<Patho_EnterData_Entity> entityList = new ArrayList<>();
		for (SaveTestDataDTO m : form) {
			if(m.getTestName() !=null) {
				Patho_MapLabTest_Entity newTest = mapLabTest_Repository.findByOldTest(m.getTestName());
				if(newTest !=null) {
					System.out.println("Old Test Name: "+ m.getTestName()+" <<Map >> New Test Name: "+newTest.getNewTest());
					List<Patho_FormatGroupMaster_Entity> formatGroupTestList = formatGroupMaster_Repository.findByFormatTest(newTest.getNewTest());
					for (Patho_FormatGroupMaster_Entity formatGroupTest : formatGroupTestList) {
						
						formatGroupTest.getFormatName(); // Format_name
						formatGroupTest.getFormatTest(); // formta _tesr
						
						Patho_ObservationMaster_Entity observation = observationMaster_Repository.findById(Long.valueOf(formatGroupTest.getTestCode()));
						Patho_FormatMaster_Entity format = formatMaster_Repository.findByFormatName(observation.getFormatName());
						Patho_MachineMaster_Entity  machine= machineMaster_Repository.findById(Long.valueOf(format.getMachineName()));
						Patho_SampleDeviceMaster_Entity sample = sampleDeviceMaster_Repository.findById(Long.valueOf(format.getSampleName()));
						Patho_EnterData_Entity entity =
								new Patho_EnterData_Entity(
										m.getDate(), 
										m.getUhid(), 
										m.getIpdno(), 
										m.getId(),
										String.valueOf(observation.getId()), 
										observation.getTestName(), 
										formatGroupTest.getFormatName()!=null ? formatGroupTest.getFormatName() : null,
										observation != null ? observation.getMGroup() : null,
										null, 
										observation != null ? observation.getMfunit1() : null,
										observation.getMfrom1() != null ? observation.getMfrom1().concat("-"+observation.getMto1()) :" ",
										null, null,
										observation != null ? observation.getMfrom1() : null,
										observation != null ? observation.getMto1() : null,
										null, null, 
										observation.getIsLable(),
										null, null, null, 
										false, 
										machine != null ? machine.getMachineName() : null,
										null, null,
										formatGroupTest.getFormatTest(),
										null, 
										"PROVISIONAL", 
										principal.getName(),
										null,
										sample != null ? sample.getSampleName() : null,
										null,
										TimeFormatChanger.getCurrentTime(), 
										TimeFormatChanger.getCurrentTime(),
										DateFormatChanger.getCurrentDate(), 
										DateFormatChanger.getCurrentDate(), 
										null,
										false
										);
						entityList.add(entity);
					}
				}
			}
		}
		
		 List<Patho_EnterData_Entity> en=enterData_Repository.saveAll(entityList);
		 if(en.size() != 0) {
				response.setStatus(200);
				response.setType(ResponseType.SUCCESS);
				response.setMessage("Saved");
				return response;
		 }
			response.setStatus(400);
			response.setType(ResponseType.RESPONSE_ERROR);
			response.setMessage("Error Happed !");
		return response;
	}

//	public BaseResponse getCollectedPatientByDate(String date, Principal principal) {
//		BaseResponse response = new BaseResponse();
//		response.setStatus(200);
//		response.setType(ResponseType.SUCCESS);
//		response.setMessage("List");
//		response.setBody(repository.getCollectedPatientList(date));
//		return response;
//	}
	
	public BaseResponse updateTime(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_PathoLabReport_Entity entity=repository.findById(id);
		if(entity != null) {
			
			entity.setTimeOfCollection(TimeFormatChanger.getCurrentTime());
			entity.setTimeOFReceived(TimeFormatChanger.getCurrentTime());
			entity.setDate(DateFormatChanger.getCurrentDate());
			
			entity=repository.save(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Collection Time And Received Time Updated!");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.WARNING);
		response.setMessage("Something wrong");
		return response;
	}
	
	public BaseResponse verifyTestAll(Long id, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		Patho_PathoLabReport_Entity entity=repository.findById(id);
		
		List<Patho_EnterData_Entity> enterData = enterData_Repository.findBySnoAndCreditYear(String.valueOf(entity.getId()),creditYear);
		for(Patho_EnterData_Entity enter:enterData) {
			enter.setStatusName("FINAL");
			enter.setVarifyBy(principal.getName());
				enterData_Repository.save(enter);
		}
		if(enterData.size() > 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("All Test Verified");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.WARNING);
		response.setMessage("Something wrong");
		return response;
	}
	
	
	/*********************************************************/
	/******** Get Collected,Received, Printed List By Type ********/
	/*********************************************************/
	public BaseResponse getOtherByDateTableAndType(String date,String type, Principal principal, Long creditYear) {
		List<Patho_PathoLabReport_Entity> list = null;
		
		if(type.equals("Collected")) {
			list=repository.findByDateAndCollectedFlag(date, true);
		}
		if(type.equals("Received")) {
			list=repository.findByDateAndReceivedFlag(date, true);
		}
		if(type.equals("Printed")) {
			list=repository.findByDateAndPrintedFlag(date, true);
		}
		if(type.equals("Unverify")) {
			list = getUnverifyedFilterList(repository.findByDateAndReceivedFlag(date, true),creditYear);
		}
		
		List<OtherByDateTableAndType> dataList = list.stream().map(m->
			new OtherByDateTableAndType(
					m.getId(),
					m.getDate(),
					m.getPatientName(), 
					m.getUhid(), 
					m.getIpdno(), 
					m.getGender(), 
					m.getAge(), 
					m.getReferredBy(), 
					m.getDept(), 
					m.getTestName(), 
					m.getTestId(), 
					m.getMobile(), 
					m.getBillNo())
		).collect(Collectors.toList());
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(dataList);
		return response;
	}
	public List<Patho_PathoLabReport_Entity> getUnverifyedFilterList(List<Patho_PathoLabReport_Entity> listEletemt,Long creditYear){
		
		List<Patho_PathoLabReport_Entity> newList=new ArrayList<>();
		
		
		for (Patho_PathoLabReport_Entity patho_PathoLabReport_Entity : listEletemt) {
		
			int counter = 0;
			List<Patho_EnterData_Entity> enterData = enterData_Repository.findBySnoAndCreditYear(String.valueOf(patho_PathoLabReport_Entity.getId()),creditYear);
			for (Patho_EnterData_Entity enter : enterData) {
				if(enter.getStatusName().equals("PROVISIONAL")) {
					counter++;
				}
			}
			if(counter>0) {
				newList.add(patho_PathoLabReport_Entity);
			}
		}
		
		return newList;
	}
	
	/*********************************************************/
	/******** Collected Button Click ********/
	/*********************************************************/
	public BaseResponse collectedRequest(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_PathoLabReport_Entity entity=repository.findById(id);
		if(entity != null) {
			entity.setCollectedFlag(false);
			entity.setReceivedFlag(true);
			entity.setTimeOFReceived(TimeFormatChanger.getCurrentTime());
			entity=repository.save(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Sample Received");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.WARNING);
		response.setMessage("Something wrong");
		return response;
	}
	
	
	/*********************************************************/
	/******** Received Button Click ********/
	/*********************************************************/
	public BaseResponse receivedRequest(String id, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
//		Patho_PathoLabReport_Entity entity=repository.findById(id);
//		if(entity != null) {
//			entity.setReceivedFlag(true);
//			entity.setTimeOFReceived(TimeFormatChanger.getCurrentTime());
//			entity=repository.save(entity);
//			response.setStatus(200);
//			response.setType(ResponseType.WARNING);
//			response.setMessage("Sample Received");
//			return response;
//		}
//		response.setStatus(400);
//		response.setType(ResponseType.WARNING);
//		response.setMessage("Something wrong");
		
		
		List<Patho_EnterData_Entity> enterData = enterData_Repository.findBySnoAndCreditYear(id,creditYear);
		
		response.setStatus(200);
		response.setType(ResponseType.WARNING);
		response.setBody(enterData);		
		response.setMessage("Sample Received");
		return response;
	}
	
	public BaseResponse saveObservationTest(List<Patho_EnterData_DTO> forms, Principal principal) {
		BaseResponse response = new BaseResponse();
		
		if (forms.size() == 0) {
			response.setStatus(300);
			response.setType(ResponseType.WARNING);
			response.setMessage("Not Found");
			return response;
		}
		
		List<Patho_EnterData_Entity> entityList = new ArrayList<Patho_EnterData_Entity>();
		for (Patho_EnterData_DTO form : forms) {
			Patho_EnterData_Entity entity = enterData_Repository.findById(Long.valueOf(form.getId()));
			BeanUtils.copyProperties(form, entity);
			entityList.add(entity);
		}
		entityList = enterData_Repository.saveAll(entityList);
		
		if (entityList.size() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Data Saved");
			return response;
		}
		return response;
	}
	
	
	public byte[] report_PDF(String id, String type,String format,Principal principal,Long creditYear) {
		try {
			String templetSrc = null;

			List<Patho_EnterData_Entity> enterData = enterData_Repository.findBySnoAndCreditYear(id,creditYear);
			
			if(enterData.size()>0) {
				
				Patho_PathoLabReport_Entity entity=repository.findById(Long.valueOf(id));
				
				Patho_EnterData_Entity enterDataEntity = enterData.get(0);
				
				ReceptionReceptionAdmission_Entity admission = admission_Repository.findByIPDNOAndCreditYear(entity.getIpdno(), entity.getCreditYear());
				
					Map<String, Object> parameters = new HashMap<>();
					parameters.put("age", entity.getAge());
					parameters.put("gender", entity.getGender());
					parameters.put("patientName", entity.getPatientName());
					
					ConsultantMaster_Entity  consultant = doctor_Repository.findById(Long.valueOf(admission.getConsultant1()));
					parameters.put("consultant", consultant!=null?consultant.getName():" ");
					
					parameters.put("uhid", entity.getUhid());
					parameters.put("statusName", enterDataEntity.getStatusName());
					
					parameters.put("bedNo", admission!=null?admission.getBEDNO():" ");
					
					parameters.put("labNo", " ");
					parameters.put("ipdno", entity.getIpdno());
					parameters.put("collectionDate", entity.getDate());
					parameters.put("collectionTime", entity.getTimeOfCollection());
					parameters.put("reportedDate", entity.getSampleReceived());
					parameters.put("reportedTime", entity.getTimeOFReceived());
					
					if(type.equals("LH")) {
						templetSrc = env.getProperty("reports.patho.lab_report_lh.file");	
					}else {
						templetSrc = env.getProperty("reports.patho.lab_report.file");
					}
					setPrinted(Long.valueOf(id),principal);
					
					List<Patho_EnterData_Entity> enterNew = enterData.stream()
							.filter(f -> f.getObvValue() != null || f.getLable().equals("1"))
//							.sorted(Comparator.comparing(Patho_EnterData_Entity::getFormattest))							
							.collect(Collectors.toList());
					
					
					for (Patho_EnterData_Entity patho_EnterData_Entity : enterNew) {
						patho_EnterData_Entity.setUserNamePrint(principal.getName());
						enterData_Repository.save(patho_EnterData_Entity);
					}
					
									
					enterNew.stream().filter(f-> 
					isNumeric(f.getObvValue()) == true ? !isBetweenRange(f.getObvValue(),f.getLow(),f.getHigh()) || f.isRecheckSample() : false)
					.forEach(i -> i.setObvValueInvalid(true));
					
					
					return factory.getPdfFormat(templetSrc, format, parameters, enterNew);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
    public static boolean isNumeric(String strNum) {
        if (strNum == null) { return false; }
        try { double d = Double.parseDouble(strNum);} catch (NumberFormatException nfe) { return false; }
        return true;
    }

    private boolean isBetweenRange(String x, String min, String max) {
        if (x != null && isNumeric(x) && isNumeric(min) && isNumeric(max)) {
            if (min != null && max != null) {
                return Float.valueOf(x) >= Float.valueOf(min) && Float.valueOf(x) <= Float.valueOf(max);
            }
        }
        return false;
    }
//	  isBetweenRange(x, min, max): boolean {
//	    if (x != null && !isNaN(x) && !isNaN(min)) {
//	      if (min.trim() != null && max.trim() != null)
//	        return Number(x) >= Number(min) && Number(x) <= Number(max);
//	    }
//	    return true;
//	  }
	public BaseResponse setPrinted(Long id, Principal principal) {
		BaseResponse response = new BaseResponse();
		Patho_PathoLabReport_Entity entity=repository.findById(id);
		if(entity != null) {
			entity.setPrintedFlag(true);
			entity=repository.save(entity);
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Sample Received");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.WARNING);
		response.setMessage("Something wrong");
		return response;
	}
	
    
	public BaseResponse verifyTest(String id,String formatName, Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		List<Patho_EnterData_Entity> enterData = enterData_Repository.findBySnoAndCreditYear(id,creditYear);
		for(Patho_EnterData_Entity entity:enterData) {
			if(entity.getFormattest().equals(formatName)) {
				entity.setStatusName("FINAL");
				entity.setVarifyBy(principal.getName());
				entity.setVarifyDate(DateFormatChanger.getCurrentDate());
				entity.setVarifyTime(TimeFormatChanger.getCurrentTime());;
				enterData_Repository.save(entity);
			}
		}
		if(enterData.size() > 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Test Verified");
			return response;
		}
		response.setStatus(400);
		response.setType(ResponseType.WARNING);
		response.setMessage("Something wrong");
		return response;
	}
	
	
	@ReadOnlyProperty
	public BaseResponse findAll(Principal principal,Long creditYear) {
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
		response.setBody(repository.findByDateAndCreditYearOrderByIdDesc(Date,creditYear));
		return response;
	}
	
	
}



@Data
@AllArgsConstructor
@NoArgsConstructor
class OtherByDateTableAndType {
	private Long id;
	private String date;
	private String name;
	private String uhid;
	private String ipdno;
	private String sex;
	private String age;
	private String consultant;
	private String dept;
	private String testName;
	private String testId;
	private String mobile;
	private String billNo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AddIdToTestList {
	private String id;
	private String uhid;
	private String ipdno;
	private String testId;
	private String testName;
	private String date;
	private String dept;
}