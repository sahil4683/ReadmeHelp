package com.as.service.hims;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.AccountEntry_Receipt_Repository;
import com.as.reporsitories.hims.ReceptionReceptionAdmission_Repository;
import com.as.reports.JasperReportFactory;
import com.as.request.Report_Bill_Request;
import com.as.response.AdmissionDischargeInterface;
import com.as.response.AdmissionDischargeResponce;
import com.as.response.FeeCollectionInterface;
import com.as.response.FeeCollectionResponse;
import com.as.utils.DateFormatChanger;
import com.as.utils.TimeFormatChanger;

@Service
public class Reports_RoutineReports_Service {

	@Autowired
	AccountEntry_Receipt_Repository repository;
	
	@Autowired
	ReceptionReceptionAdmission_Repository admission_repository;
	
	@Autowired
	JasperReportFactory factory;

	@Autowired
	Environment env;
	
	public byte[] report_PDF(Report_Bill_Request request,Long creditYear) {
		try {
			
			if(request.getType().equals("Admission_Discharge")) {
				String templetSrc = env.getProperty("reports.Report_Report_Admission_Discharge.file");
				
				List<AdmissionDischargeInterface> tesmList=admission_repository.getAdmissionDischarge(
						request.getFromDate(),
						request.getToDate(),
						request.getPatientName(),
						request.getOrganizationName(),
						request.getTypeOfPatient(),
						request.getDischargeyn(),
						request.getConsultant(),
						creditYear
						);
				
				List<AdmissionDischargeResponce> MainList= tesmList.stream().map(m-> 
			       new AdmissionDischargeResponce(
			    		   DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(m.getDate()), 
			    		   TimeFormatChanger.Time24HourTo12Hour(m.getTime()), 
			    		   m.getUhid(),
			    		   m.getName(), 
			    		   m.getIpd(), 
			    		   m.getDischargeDate()!=null ? DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(m.getDischargeDate()) : "-",
			    		   m.getDischargeTime()!=null ? TimeFormatChanger.Time24HourTo12Hour(m.getDischargeTime()) : "-",
			    		   m.getAge() +"/"+m.getSex(),
			    		   m.getBedno(),
			    		   m.getGroupname(),
			    		   m.getConsultant(),
			    		   m.getOrganization(),
			    		   m.getInsurance_com(), 
			    		   m.getStatus())
		        ).collect(Collectors.toList());
				
				Integer count=MainList.size();

				tesmList.forEach(f-> System.out.println(f.getDischargeDate()));
				
		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "ADMISSION / DISCHARGE REGISTER");
				if(request.getFromDate()!=null && request.getToDate()!=null) {
					parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");	
				}
				
				parameters.put("patientTotal", String.valueOf(count));
				
				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
			
			
			if(request.getType().equals("DateWise")) {
				String templetSrc = env.getProperty("reports.Report_Report_Fee_Collection.file");
				
				List<FeeCollectionInterface> tesmList=new ArrayList<FeeCollectionInterface>();
				
				String s = request.getFromDate();
		        String e = request.getToDate();
		        LocalDate start = LocalDate.parse(s);
		        LocalDate end = LocalDate.parse(e);
		        while (!start.isAfter(end)) {
		            tesmList.add(repository.getFeeCollectionDateWise(String.valueOf(start),creditYear));
		            start = start.plusDays(1);
		        }
				
		       List<FeeCollectionResponse> MainList= tesmList.stream().map(m-> 
			        new FeeCollectionResponse(DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(m.getDate()), 
			        		m.getIpd_Diposit(), m.getIpd_Bill(), m.getIpd_Refund(),
			        		m.getOpd_Diposit(), m.getOpd_Bill(), m.getOpd_Refund(),
			        		m.getLab_Diposit(), m.getLab_Bill(), m.getLab_Refund(),
			        		m.getRadio_Diposit(), m.getRadio_Bill(), m.getRadio_Refund(),
			        		m.getHealthCheckup_Diposit(), m.getHealthCheckup_Bill(), m.getHealthCheckup_Refund(),
			        		0, 0, 
			        		m.getReceipt_Total(),
			        		m.getReceipt_Refund(),
			        		m.getNet_Total())
		        ).collect(Collectors.toList());

				Integer aTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getA())).sum();
				Integer bTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getB())).sum();
				Integer cTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getC())).sum();
				Integer dTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getD())).sum();
				Integer eTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getE())).sum();
				Integer fTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getF())).sum();
				Integer gTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getG())).sum();
				Integer hTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getH())).sum();
				Integer iTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getI())).sum();
				Integer jTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getJ())).sum();
				Integer kTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getK())).sum();
				Integer lTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getL())).sum();
				Integer mTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getM())).sum();
				Integer nTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getN())).sum();
				Integer oTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getO())).sum();
				Integer pTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getP())).sum();
				Integer qTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getQ())).sum();
				Integer xTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getX())).sum();
				Integer yTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getY())).sum();
				Integer xyTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getXy())).sum();
				
		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "Date wise Cash Summary");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				
				parameters.put("aTotal", aTotal);
				parameters.put("bTotal", bTotal);
				parameters.put("cTotal", cTotal);
				parameters.put("dTotal", dTotal);
				parameters.put("eTotal", eTotal);
				parameters.put("fTotal", fTotal);
				parameters.put("gTotal", gTotal);
				parameters.put("hTotal", hTotal);
				parameters.put("iTotal", iTotal);
				parameters.put("jTotal", jTotal);
				parameters.put("kTotal", kTotal);
				parameters.put("lTotal", lTotal);
				parameters.put("mTotal", mTotal);
				parameters.put("nTotal", nTotal);
				parameters.put("oTotal", oTotal);
				parameters.put("pTotal", pTotal);
				parameters.put("qTotal", qTotal);
				parameters.put("xTotal", xTotal);
				parameters.put("yTotal", yTotal);
				parameters.put("xyTotal", xyTotal);

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
			if(request.getType().equals("UserWise")) {
				String templetSrc = env.getProperty("reports.Report_Report_Fee_Collection.file");
				
				List<FeeCollectionInterface> tesmList=new ArrayList<FeeCollectionInterface>();
				
				String s = request.getFromDate();
		        String e = request.getToDate();
		        LocalDate start = LocalDate.parse(s);
		        LocalDate end = LocalDate.parse(e);
		        while (!start.isAfter(end)) {
		            tesmList.add(repository.getFeeCollectionDateWiseAndUser((String.valueOf(start)),request.getUserName(),creditYear));
		            start = start.plusDays(1);
		        }
				
		       List<FeeCollectionResponse> MainList= tesmList.stream().map(m-> 
			        new FeeCollectionResponse(DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(m.getDate()), 
			        		m.getIpd_Diposit(), m.getIpd_Bill(), m.getIpd_Refund(),
			        		m.getOpd_Diposit(), m.getOpd_Bill(), m.getOpd_Refund(),
			        		m.getLab_Diposit(), m.getLab_Bill(), m.getLab_Refund(),
			        		m.getRadio_Diposit(), m.getRadio_Bill(), m.getRadio_Refund(),
			        		m.getHealthCheckup_Diposit(), m.getHealthCheckup_Bill(), m.getHealthCheckup_Refund(),
			        		0, 0, 
			        		m.getReceipt_Total(),
			        		m.getReceipt_Refund(),
			        		m.getNet_Total())
		        ).collect(Collectors.toList());;

				Integer aTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getA())).sum();
				Integer bTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getB())).sum();
				Integer cTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getC())).sum();
				Integer dTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getD())).sum();
				Integer eTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getE())).sum();
				Integer fTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getF())).sum();
				Integer gTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getG())).sum();
				Integer hTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getH())).sum();
				Integer iTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getI())).sum();
				Integer jTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getJ())).sum();
				Integer kTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getK())).sum();
				Integer lTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getL())).sum();
				Integer mTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getM())).sum();
				Integer nTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getN())).sum();
				Integer oTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getO())).sum();
				Integer pTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getP())).sum();
				Integer qTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getQ())).sum();
				Integer xTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getX())).sum();
				Integer yTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getY())).sum();
				Integer xyTotal=MainList.stream().mapToInt(i -> Integer.valueOf(i.getXy())).sum();
				
		        Map<String, Object> parameters = new HashMap<>();
				parameters.put("billType", "Date wise Cash Summary");
				parameters.put("fullDate", "From Date : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getFromDate())+" To : "+DateFormatChanger.YYYYMMDD_TO_DDMMYYYY(request.getToDate())+",");
				
				parameters.put("aTotal", aTotal);
				parameters.put("bTotal", bTotal);
				parameters.put("cTotal", cTotal);
				parameters.put("dTotal", dTotal);
				parameters.put("eTotal", eTotal);
				parameters.put("fTotal", fTotal);
				parameters.put("gTotal", gTotal);
				parameters.put("hTotal", hTotal);
				parameters.put("iTotal", iTotal);
				parameters.put("jTotal", jTotal);
				parameters.put("kTotal", kTotal);
				parameters.put("lTotal", lTotal);
				parameters.put("mTotal", mTotal);
				parameters.put("nTotal", nTotal);
				parameters.put("oTotal", oTotal);
				parameters.put("pTotal", pTotal);
				parameters.put("qTotal", qTotal);
				parameters.put("xTotal", xTotal);
				parameters.put("yTotal", yTotal);
				parameters.put("xyTotal", xyTotal);

				return factory.getPdfFormat(templetSrc, request.getFormat(), parameters, MainList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
}
