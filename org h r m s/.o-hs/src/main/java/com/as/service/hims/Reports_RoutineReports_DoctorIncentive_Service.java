package com.as.service.hims;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.as.h_entities.hims.MasterOtherMasters1_DoctorIncentiveMaster_Entity;
import com.as.reporsitories.hims.ConsultantMaster_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_DoctorIncentiveMaster_Repository;
import com.as.reports.JasperReportFactory;
import com.as.request.Report_Bill_Request;
import com.as.response.BaseResponse;
import com.as.response.ResponseType;
import com.as.response.TransactedDoctorResponse;
import com.as.response.TransactedDoctorResponseMap;
import com.as.utils.DateFormatChanger;

@Service
public class Reports_RoutineReports_DoctorIncentive_Service {

	@Autowired
	JasperReportFactory factory;

	@Autowired
	Environment env;

	@Autowired
	ConsultantMaster_Repository consultantMaster_Repository;

	@Autowired
	MasterOtherMasters1_DoctorIncentiveMaster_Repository incentiveMaster_Repository;
	
	public BaseResponse getTransactedDoctorList(String fromDate, String toDate, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(consultantMaster_Repository.getTransactedDoctorList(fromDate, toDate,creditYear));
		return response;
	}


	public byte[] report_PDF(Report_Bill_Request request,Long creditYear) {
		try {
			String templetSrc = env.getProperty("reports.Report_DoctorIncentive.file");
			List<TransactedDoctorResponse> tesmList = consultantMaster_Repository.getTransactedDoctorData(request.getFromDate(), request.getToDate(),request.getConsultant(),creditYear);
			MasterOtherMasters1_DoctorIncentiveMaster_Entity  rate = incentiveMaster_Repository.findByDoctorNameView(request.getConsultant());
			List<TransactedDoctorResponseMap> MainList = tesmList.stream()
					.map(m -> new TransactedDoctorResponseMap(
							m.getUhid(), 
							m.getBillNo(), 
							m.getIpdno(), 
							DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(m.getDate()), 
							m.getPatientName(),
							m.getTypeOfPatient(), 
							m.getPatientType(), 
							m.getOrganization(),
							m.getGroupName(),
							m.getParticulars(), 
							String.valueOf(m.getTotal()),
							String.valueOf(m.getNetTotal()), 
							calculateDoctorPrecent(m.getGroupName(),String.valueOf(m.getTotal()),rate)
							)
						).collect(Collectors.toList());

			Integer totalSum=MainList.stream().mapToInt(i -> Integer.valueOf(i.getTotal())).sum();
			Integer netSum=MainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal())).sum();
			Integer incentiveSum=MainList.stream().mapToInt(i -> Integer.valueOf(i.getIncentive())).sum();

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("fullDate", "From Date : " + DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate()) + " To : " + DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate()) + ",");
			parameters.put("doctorName", "Doctor Name : "+request.getConsultant());
			parameters.put("totalSum", String.valueOf(totalSum));
			parameters.put("netSum", String.valueOf(netSum));
			parameters.put("incentiveSum", String.valueOf(incentiveSum));

			return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String calculateDoctorPrecent(String testName, String amount, MasterOtherMasters1_DoctorIncentiveMaster_Entity  rate) {
		double cal = 0;
		if(testName.equals("STAY CHARGES")) {
			 cal = (Double.valueOf(rate.getStayDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getStayDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("VISIT CHARGES")) {
			 cal = (Double.valueOf(rate.getVisitDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getVisitDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("VISIT REFFERED")) {
			 cal = (Double.valueOf(rate.getVisitRefferedDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getVisitRefferedDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("SONOGRAPHY CHARGES")) {
			 cal = (Double.valueOf(rate.getScDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getScDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		
		if(testName.equals("CONSULTATION")) {
			 cal = (Double.valueOf(rate.getConsultationDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getConsultationDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("ECG CHARGES")) {
			 cal = (Double.valueOf(rate.getEcgDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getEcgDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("ECHO") || testName.equals("TMT")) {
			 cal = (Double.valueOf(rate.getEchoDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getEchoDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		if(testName.equals("OTHER CHARGES")) {
			 cal = (Double.valueOf(rate.getOtherDoctor()) / 100) * Double.valueOf(amount);
			 System.out.println("["+testName+"] :"+ amount+" % "+rate.getOtherDoctor()+" = "+cal);
			 return String.valueOf(Math.round(cal));
		}
		
		return String.valueOf("0");
	}

}
