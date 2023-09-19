package com.as.service.hims;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.AccountEntry_Payment_Repository;
import com.as.reporsitories.hims.AccountEntry_Receipt_Repository;
import com.as.reporsitories.hims.IpdBill_FinalIpdBill_Repository;
import com.as.reporsitories.hims.ReceptionBillLab_Repository;
import com.as.reporsitories.hims.ReceptionBillOpdHealthCheckup_Repository;
import com.as.reporsitories.hims.ReceptionBillOpd_Repository;
import com.as.reporsitories.hims.ReceptionBillRadio_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundLab_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundOpdHealthCheckup_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundOpd_Repository;
import com.as.reporsitories.hims.ReceptionBill_RefundRadio_Repository;
import com.as.reports.JasperReportFactory;
import com.as.request.Report_Bill_Request;
import com.as.response.ReportBillInterface;
import com.as.utils.DateFormatChanger;

@Service
public class Reports_Bill_Service {

	@Autowired
	JasperReportFactory factory;

	@Autowired
	Environment env;

	@Autowired
	ReceptionBillOpd_Repository opd_Repository;
	
	@Autowired
	ReceptionBill_RefundOpd_Repository opd_refund_Repository;

	@Autowired
	ReceptionBillRadio_Repository radio_Repository;
	
	@Autowired
	ReceptionBill_RefundRadio_Repository radio_refund_Repository;

	@Autowired
	ReceptionBillLab_Repository lab_Repository;
	
	@Autowired
	ReceptionBill_RefundLab_Repository lab_refund_Repository;
	
	@Autowired
	ReceptionBillOpdHealthCheckup_Repository healthCheckup_Repository;
	
	@Autowired
	ReceptionBill_RefundOpdHealthCheckup_Repository healthCheckup_refund_Repository;
	
	@Autowired
	IpdBill_FinalIpdBill_Repository ipd_Repository;
	@Autowired
	AccountEntry_Payment_Repository payment_Repository;
	@Autowired
	AccountEntry_Receipt_Repository receipt_Repository;
		
	public byte[] report_PDF(Report_Bill_Request request,Long yearCd) {
		try {
			String templetSrc = null;
			
			
			if(request.getType().equals("IPDReceiptRegister")) {
				List<ReportBillInterface> mainList = receipt_Repository.getBillReport(
						request.getFromDate(),
						request.getToDate(),
						yearCd,
						request.getModeOfPayment(),
						request.getPart(),
						request.getToAccount(),
						request.getDepartment()
						);
				if(mainList.isEmpty())return null;
				Integer grandTotal=mainList.stream().mapToInt(i -> Integer.valueOf(i.getReceipt())).sum();
				
				Map<String, Object> parameters = new HashMap<>();

				parameters.put("billType", "IPD REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("grandTotal", String.valueOf(grandTotal));
				
				templetSrc = env.getProperty("reports.Report_IpdReceiptRegister.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			if(request.getType().equals("IPDRegister")) {
				List<ReportBillInterface> mainList = ipd_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getSubDept(),
						request.getConsultant(),
						yearCd
						);
				if(mainList.isEmpty())return null;
				Integer count=mainList.size();
				Integer sumGrandTotal=mainList.stream().mapToInt(i -> Math.round(Float.valueOf(i.getGrandTotal()!=null?i.getGrandTotal():"0"))).sum();
				Integer sumOtherDiscount=mainList.stream().mapToInt(i -> Integer.valueOf(Math.round(Float.valueOf(i.getOtherDiscount()!=null?i.getOtherDiscount():"0")))).sum();
				Integer sumDiscount=mainList.stream().mapToInt(i -> Integer.valueOf(i.getDiscount()!=null?i.getDiscount():"0")).sum();
				Integer sumAdvance=mainList.stream().mapToInt(i -> Integer.valueOf(i.getAdvance()!=null?i.getAdvance():"0")).sum();
				Integer sumTotal=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTotal()!=null?i.getTotal():"0")).sum();
				Map<String, Object> parameters = new HashMap<>();

				parameters.put("billType", "IPD REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				
				parameters.put("sumGrandTotal", String.valueOf(sumGrandTotal));
				parameters.put("sumOtherDiscount", String.valueOf(sumOtherDiscount));
				parameters.put("sumDiscount", String.valueOf(sumDiscount));
				parameters.put("sumAdvance", String.valueOf(sumAdvance));
				parameters.put("sumTotal", String.valueOf(sumTotal));
				templetSrc = env.getProperty("reports.Report_Bill_IpdRegister.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			
			if(request.getType().equals("HealthCheckup")) {
				List<ReportBillInterface> mainList = healthCheckup_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer totalSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTotal())).sum();
				Integer NetTotalSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal())).sum();
				Integer PaidSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getPaid())).sum();
				Integer DueSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getDue())).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "HealthCheckup REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("totalSum", String.valueOf(totalSum));
				parameters.put("netTotalSum", String.valueOf(NetTotalSum));
				parameters.put("paidSum", String.valueOf(PaidSum));
				parameters.put("dueSum", String.valueOf(DueSum));
				parameters.put("collected", String.valueOf(0));
				templetSrc = env.getProperty("reports.Report_Bill_HealthCheckup.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			if(request.getType().equals("HealthCheckup_refund")) {
				List<ReportBillInterface> mainList = healthCheckup_refund_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer totalSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTotal())).sum();
				Integer NetTotalSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal())).sum();
				Integer PaidSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getPaid())).sum();
				Integer DueSum=mainList.stream().mapToInt(i -> Integer.valueOf(i.getDue())).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "HealthCheckup Refund REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("totalSum", String.valueOf(totalSum));
				parameters.put("netTotalSum", String.valueOf(NetTotalSum));
				parameters.put("paidSum", String.valueOf(PaidSum));
				parameters.put("dueSum", String.valueOf(DueSum));
				parameters.put("collected", String.valueOf(0));
				templetSrc = env.getProperty("reports.Report_Bill_HealthCheckup.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			
			if(request.getType().equals("opd")) {
				List<ReportBillInterface> mainList = opd_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				
				if(mainList.isEmpty())return null;
				
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "OPD REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			
			if(request.getType().equals("opd_refund")) {
				List<ReportBillInterface> mainList = opd_refund_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				
				if(mainList.isEmpty())return null;
				
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "OPD Refund REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			
			
			if(request.getType().equals("lab")) {
				List<ReportBillInterface> mainList = lab_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "LAB REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			if(request.getType().equals("lab_refund")) {
				List<ReportBillInterface> mainList = lab_refund_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "LAB Refund REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			if(request.getType().equals("radio")) {
				List<ReportBillInterface> mainList = radio_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RADIO REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			if(request.getType().equals("radio_refund")) {
				List<ReportBillInterface> mainList = radio_refund_Repository.getBillReport(request.getFromDate(),
						request.getToDate(),
						request.getCreditAgainst(),
						request.getOrganizationName(),
						request.getUserName(),
						request.getRefBy(),
						request.getBillType(),
						request.getSubDept(),
						request.getTypeOfPatient(),
						request.getConsultant(),
						yearCd,
						request.getModeOfPayment()
						);
				if(mainList.isEmpty())return null;
				Integer Concession=mainList.stream().mapToInt(i -> Integer.valueOf(i.getConcession()!=null && i.getConcession().length()>0?i.getConcession():"0")).sum();
				Integer Trust=mainList.stream().mapToInt(i -> Integer.valueOf(i.getTrust()!=null && i.getTrust().length()>0?i.getTrust().trim():"0")).sum();
				Integer Total=mainList.stream().mapToInt(i -> Integer.valueOf(i.getNetTotal()!=null && i.getNetTotal().length()>0?i.getNetTotal():"0")).sum();
				Integer count=mainList.size();
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "RADIO Refund REGISTER");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				if(request.getOrganizationName()!=null) {
					parameters.put("organization", "Organization : "+request.getOrganizationName());	
				}
				if(request.getTypeOfPatient()!=null) {
					parameters.put("patientType", "Patient Type : "+request.getTypeOfPatient());	
				}
				parameters.put("groupTotal", String.valueOf(count));
				parameters.put("totalCount", String.valueOf(count));
				parameters.put("sumConcession", String.valueOf(Concession));
				parameters.put("sumTrust", String.valueOf(Trust));
				parameters.put("sumNetTotal", String.valueOf(Total));
				templetSrc = env.getProperty("reports.Report_Bill.file");
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, mainList);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
