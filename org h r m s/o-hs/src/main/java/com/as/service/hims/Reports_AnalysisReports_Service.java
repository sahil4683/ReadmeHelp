package com.as.service.hims;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.AccountEntry_Receipt_Repository;
import com.as.reporsitories.hims.MasterOtherMasters1_Group_Repository;
import com.as.reporsitories.hims.ReceptionBillLab_Repository;
import com.as.reporsitories.hims.ReceptionBillOpdHealthCheckup_Repository;
import com.as.reporsitories.hims.ReceptionBillOpd_Repository;
import com.as.reporsitories.hims.ReceptionBillRadio_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reports.JasperReportFactory;
import com.as.request.Report_Bill_Request;
import com.as.response.BaseResponse;
import com.as.response.GroupWiseTestInterface;
import com.as.response.GroupWiseTestResponseSub;
import com.as.response.MisInterface;
import com.as.response.ResponseType;
import com.as.utils.DateFormatChanger;

@Service
public class Reports_AnalysisReports_Service {

	@Autowired
	AccountEntry_Receipt_Repository repository;
	
	@Autowired
	ReceptionReceptionAdmission_Repository admission_repository;
	
	@Autowired
	JasperReportFactory factory;

	@Autowired
	MasterOtherMasters1_Group_Repository groupRepository;
	
	@Autowired
	ReceptionBillOpd_Repository opdRepository; 
	
	@Autowired
	ReceptionBillLab_Repository labRepository;
	
	@Autowired
	ReceptionBillRadio_Repository radioRepository;
	
	@Autowired
	ReceptionBillOpdHealthCheckup_Repository hcRepository;


	@Autowired
	Environment env;
	
	public byte[] report_PDF(Report_Bill_Request request,Long creditYear) {
		try {
			
			if(request.getType().equals("MIS")) {
				String templetSrc = env.getProperty("reports.Report_MIS.file");
				
				MisInterface mis=repository.getMis(request.getFromDate(), request.getToDate(),creditYear);

		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "Management Information System");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				
				
				parameters.put("getNewOpdBillPatients", mis.getNewOpdBillPatients());
				parameters.put("getOldOpdBillPatients", mis.getOldOpdBillPatients());
				parameters.put("getIPDPatients", mis.getIPDPatients());
				parameters.put("getIPDDischarge", mis.getIPDDischarge());
				parameters.put("getTotalOpdBillPatients", mis.getTotalOpdBillPatients());
				parameters.put("getIPDTotal", mis.getIPDTotal());
				parameters.put("getTotalLabBillPatients", mis.getTotalLabBillPatients());
				parameters.put("getTotalRadioBillPatients", mis.getTotalRadioBillPatients());
				parameters.put("getTotalhealthCheckupBillPatients", mis.getTotalhealthCheckupBillPatients());
				
				
				parameters.put("getTotalAdmission", mis.getTotalAdmission());
				parameters.put("getTotalDischarge", mis.getTotalDischarge());
				parameters.put("getTotalDeath", mis.getTotalDeath());
				
				
				parameters.put("getSumOpdBill", mis.getSumOpdBill());
				parameters.put("getSumOpdRefund", mis.getSumOpdRefund());
				parameters.put("getSumLabBill", mis.getSumLabBill());
				parameters.put("getSumLabRefund", mis.getSumLabRefund());
				parameters.put("getSumRadioBill", mis.getSumRadioBill());
				parameters.put("getSumRadioRefund", mis.getSumRadioRefund());
				parameters.put("getSumHealthCheckupBill", mis.getSumHealthCheckupBill());
				parameters.put("getSumHealthCheckupRefund", mis.getSumHealthCheckupRefund());
				parameters.put("getTotalSumBill", mis.getTotalSumBill());
				

				parameters.put("getSumOpdBillCash", mis.getSumOpdBillCash());
				parameters.put("getSumOpdBillCheque", mis.getSumOpdBillCheque());
				parameters.put("getSumOpdBillPlasticMoney", mis.getSumOpdBillPlasticMoney());
				parameters.put("getSumOpdDepositCash", mis.getSumOpdDepositCash());
				parameters.put("getSumOpdDepositCheque", mis.getSumOpdDepositCheque());
				parameters.put("getSumOpdDepositPlasticMoney", mis.getSumOpdDepositPlasticMoney());
				parameters.put("getSumOpdRefundCash", mis.getSumOpdRefundCash());
				parameters.put("getSumOpdRefundCheque", mis.getSumOpdRefundCheque());
				parameters.put("getSumOpdRefundPlasticMoney", mis.getSumOpdRefundPlasticMoney());
				

				parameters.put("getSumHealthCheckupBillCash", mis.getSumHealthCheckupBillCash());
				parameters.put("getSumHealthCheckupBillCheque", mis.getSumHealthCheckupBillCheque());
				parameters.put("getSumHealthCheckupBillPlasticMoney", mis.getSumHealthCheckupBillPlasticMoney());
				parameters.put("getSumHealthCheckupDepositCash", mis.getSumHealthCheckupDepositCash());
				parameters.put("getSumHealthCheckupDepositCheque", mis.getSumHealthCheckupDepositCheque());
				parameters.put("getSumHealthCheckupDepositPlasticMoney", mis.getSumHealthCheckupDepositPlasticMoney());
				parameters.put("getSumHealthCheckupRefundCash", mis.getSumHealthCheckupRefundCash());
				parameters.put("getSumHealthCheckupRefundCheque", mis.getSumHealthCheckupRefundCheque());
				parameters.put("getSumHealthCheckupRefundPlasticMoney", mis.getSumHealthCheckupRefundPlasticMoney());
				
				
				parameters.put("getSumLabBillCash", mis.getSumLabBillCash());
				parameters.put("getSumLabBillCheque", mis.getSumLabBillCheque());
				parameters.put("getSumLabBillPlasticMoney", mis.getSumLabBillPlasticMoney());
				parameters.put("getSumLabDepositCash", mis.getSumLabDepositCash());
				parameters.put("getSumLabDepositCheque", mis.getSumLabDepositCheque());
				parameters.put("getSumLabDepositPlasticMoney", mis.getSumLabDepositPlasticMoney());
				parameters.put("getSumLabRefundCash", mis.getSumLabRefundCash());
				parameters.put("getSumLabRefundCheque", mis.getSumLabRefundCheque());
				parameters.put("getSumLabRefundPlasticMoney", mis.getSumLabRefundPlasticMoney());

				
				parameters.put("getSumRadioBillCash", mis.getSumRadioBillCash());
				parameters.put("getSumRadioBillCheque", mis.getSumRadioBillCheque());
				parameters.put("getSumRadioBillPlasticMoney", mis.getSumRadioBillPlasticMoney());
				parameters.put("getSumRadioDepositCash", mis.getSumRadioDepositCash());
				parameters.put("getSumRadioDepositCheque", mis.getSumRadioDepositCheque());
				parameters.put("getSumRadioDepositPlasticMoney", mis.getSumRadioDepositPlasticMoney());
				parameters.put("getSumRadioRefundCash", mis.getSumRadioRefundCash());
				parameters.put("getSumRadioRefundCheque", mis.getSumRadioRefundCheque());
				parameters.put("getSumRadioRefundPlasticMoney", mis.getSumRadioRefundPlasticMoney());
				
				
				parameters.put("getSumIpdBillCash", mis.getSumIpdBillCash());
				parameters.put("getSumIpdBillCheque", mis.getSumIpdBillCheque());
				parameters.put("getSumIpdBillPlasticMoney", mis.getSumIpdBillPlasticMoney());
				parameters.put("getSumIpdDepositCash", mis.getSumIpdDepositCash());
				parameters.put("getSumIpdDepositCheque", mis.getSumIpdDepositCheque());
				parameters.put("getSumIpdDepositPlasticMoney", mis.getSumIpdDepositPlasticMoney());
				parameters.put("getSumIpdRefundCash", mis.getSumIpdRefundCash());
				parameters.put("getSumIpdRefundCheque", mis.getSumIpdRefundCheque());
				parameters.put("getSumIpdRefundPlasticMoney", mis.getSumIpdRefundPlasticMoney());
				
				
				parameters.put("getTotalCash", mis.getTotalCash());
				parameters.put("getTotalCheque", mis.getTotalCheque());
				parameters.put("getTotalPlasticMoney", mis.getTotalPlasticMoney());
				
				parameters.put("getTotalRefundCash", mis.getTotalRefundCash());
				parameters.put("getTotalRefundCheque", mis.getTotalRefundCheque());
				parameters.put("getTotalRefundPlasticMoney", mis.getTotalRefundPlasticMoney());
				
				parameters.put("getNetReceiptCash", mis.getNetReceiptCash());
				parameters.put("getNetReceiptCheque", mis.getNetReceiptCheque());
				parameters.put("getNetReceiptPlasticMoney", mis.getNetReceiptPlasticMoney());

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, null);
			}

			
			if(request.getType().equals("GroupWiseTest_OPD")) {
				String templetSrc = env.getProperty("reports.Report_GroupWise_New.file");

				List<GroupWiseTestInterface> tempList=opdRepository.getGroupWiseTest(
						request.getFromDate(),
						request.getToDate(),
						request.getGroupName(),
						request.getTestName(),
						request.getConsultant(),
						creditYear
						);
				
				List<GroupWiseTestResponseSub> MainList= tempList.stream().map(m-> 
			       new GroupWiseTestResponseSub(
			    		   m.getUhid(), m.getDate(), m.getBillno(), m.getOperator(), m.getName(), m.getTestName(), m.getRate(), m.getDoctor())
		        ).collect(Collectors.toList());

		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RECEIPT ANALYSIS : OPD");
				parameters.put("fullDate", "From Date : "+request.getFromDate()+" To : "+request.getToDate()+",");
				Integer doctorTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getRate())).sum();
				Integer testCount=MainList.size();
				parameters.put("doctorName", request.getConsultant());
				parameters.put("testCount", String.valueOf(testCount));
				parameters.put("doctorTotal", String.valueOf(doctorTotal));
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
			if(request.getType().equals("GroupWiseTest_LAB")) {
				String templetSrc = env.getProperty("reports.Report_GroupWise_New.file");
			
				List<GroupWiseTestInterface> tempList=labRepository.getGroupWiseTest(
						request.getFromDate(),
						request.getToDate(),
						request.getGroupName(),
						request.getTestName(),
						request.getConsultant(),
						creditYear
						);
				
				List<GroupWiseTestResponseSub> MainList= tempList.stream().map(m-> 
			       new GroupWiseTestResponseSub(
			    		   m.getUhid(), m.getDate(), m.getBillno(), m.getOperator(), m.getName(), m.getTestName(), m.getRate(), m.getDoctor())
		        ).collect(Collectors.toList());

		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RECEIPT ANALYSIS : LAB");
				parameters.put("fullDate", "From Date : "+request.getFromDate()+" To : "+request.getToDate()+",");
				Integer doctorTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getRate())).sum();
				Integer testCount=MainList.size();
				parameters.put("doctorName", request.getConsultant());
				parameters.put("testCount", String.valueOf(testCount));
				parameters.put("doctorTotal", String.valueOf(doctorTotal));

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
			if(request.getType().equals("GroupWiseTest_RADIO")) {
				String templetSrc = env.getProperty("reports.Report_GroupWise_New.file");
			
				List<GroupWiseTestInterface> tempList=radioRepository.getGroupWiseTest(
						request.getFromDate(),
						request.getToDate(),
						request.getGroupName(),
						request.getTestName(),
						request.getConsultant(),
						creditYear
						);
				
				List<GroupWiseTestResponseSub> MainList= tempList.stream().map(m-> 
			       new GroupWiseTestResponseSub(
			    		   m.getUhid(), m.getDate(), m.getBillno(), m.getOperator(), m.getName(), m.getTestName(), m.getRate(), m.getDoctor())
		        ).collect(Collectors.toList());

		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RECEIPT ANALYSIS : RADIO");
				parameters.put("fullDate", "From Date : "+request.getFromDate()+" To : "+request.getToDate()+",");
				Integer doctorTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getRate())).sum();
				Integer testCount=MainList.size();
				parameters.put("doctorName", request.getConsultant());
				parameters.put("testCount", String.valueOf(testCount));
				parameters.put("doctorTotal", String.valueOf(doctorTotal));

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
			if(request.getType().equals("GroupWiseTest_HC")) {
				String templetSrc = env.getProperty("reports.Report_GroupWise_New.file");

				List<GroupWiseTestInterface> tempList=hcRepository.getGroupWiseTest(
						request.getFromDate(),
						request.getToDate(),
						request.getGroupName(),
						request.getTestName(),
						request.getConsultant(),
						creditYear
						);
				
				List<GroupWiseTestResponseSub> MainList= tempList.stream().map(m-> 
			       new GroupWiseTestResponseSub(
			    		   m.getUhid(), m.getDate(), m.getBillno(), m.getOperator(), m.getName(), m.getTestName(), m.getRate(), m.getDoctor())
		        ).collect(Collectors.toList());

		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RECEIPT ANALYSIS : HEALTHCHECKUP");
				parameters.put("fullDate", "From Date : "+request.getFromDate()+" To : "+request.getToDate()+",");
				Integer doctorTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getRate())).sum();
				Integer testCount=MainList.size();
				parameters.put("doctorName", request.getConsultant());
				parameters.put("testCount", String.valueOf(testCount));
				parameters.put("doctorTotal", String.valueOf(doctorTotal));

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	public BaseResponse getReport(Report_Bill_Request request,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.getMis(request.getFromDate(),request.getToDate(),creditYear));
		return response;
	}
	
}
