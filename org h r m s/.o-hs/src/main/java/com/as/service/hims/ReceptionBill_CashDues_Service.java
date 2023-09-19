package com.as.service.hims;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.as.h_dto.hims.ReceptionBill_CashDues_DTO;
import com.as.h_entities.hims.ReceptionBillLab_Entity;
import com.as.h_entities.hims.ReceptionBillOpdHealthCheckup_Entity;
import com.as.h_entities.hims.ReceptionBillOpd_Entity;
import com.as.h_entities.hims.ReceptionBillRadio_Entity;
import com.as.h_entities.hims.ReceptionBill_CashDues_Entity;
import com.as.reporsitories.hims.ReceptionBillLab_Repository;
import com.as.reporsitories.hims.ReceptionBillOpdHealthCheckup_Repository;
import com.as.reporsitories.hims.ReceptionBillOpd_Repository;
import com.as.reporsitories.hims.ReceptionBillRadio_Repository;
import com.as.reporsitories.hims.ReceptionBill_CashDues_Repository;
import com.as.reporsitories.hims.ReceptionReceptionRegistration_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.BaseResponse;
import com.as.response.PendingDueInterface;
import com.as.response.ResponseType;
@Service
public class ReceptionBill_CashDues_Service {

	@Autowired
	ReceptionBill_CashDues_Repository repository;
	// Bill
	@Autowired
	ReceptionBillOpd_Repository opd_Repository;

	@Autowired
	ReceptionBillRadio_Repository radio_Repository;

	@Autowired
	ReceptionBillLab_Repository lab_Repository;

	@Autowired
	ReceptionBillOpdHealthCheckup_Repository healthCheckup_Repository;

	@Autowired
	ReceptionReceptionRegistration_Repository patient_repository;

//	@Value("${cash_due.report.file}")
//	private String templetSrc;
	
	@Autowired
	JasperReportFactory factory;
	
	@Autowired
	Environment env;

	public BaseResponse save(ReceptionBill_CashDues_DTO form, Principal principal, Long creditYear) {
		BaseResponse response = new BaseResponse();

		ReceptionBill_CashDues_Entity entity = new ReceptionBill_CashDues_Entity();

		BeanUtils.copyProperties(form, entity);

		/*
		 * Other Modification On Entity
		 */
		Long nextReceiptNo = repository.getNextReceiptNo();
		nextReceiptNo = (nextReceiptNo == null ? 1 : nextReceiptNo + 1);

		entity.setReceiptNo(String.valueOf(nextReceiptNo));
		entity.setUserName(principal.getName());

		/*
		 * Update To Reception - Bill Module
		 * */
		if(form.getDept().equals("LAB")) {
			ReceptionBillLab_Entity dueUpdate = lab_Repository.findByBillNoAndCreditYear(form.getBillNo(),creditYear);
			dueUpdate.setPaidAmount(form.getAmount()); // 7
			dueUpdate.setDue(String.valueOf(Integer.valueOf(dueUpdate.getDue())-Integer.valueOf(form.getAmount()))); // 7-2=5
			lab_Repository.save(dueUpdate); }
		if(form.getDept().equals("OPD")) {
			ReceptionBillOpd_Entity dueUpdate = opd_Repository.findByBillNoAndCreditYear(form.getBillNo(),creditYear);
			dueUpdate.setPaidAmount(form.getAmount()); // 7
			dueUpdate.setDue(String.valueOf(Integer.valueOf(dueUpdate.getDue())-Integer.valueOf(form.getAmount()))); // 7-2=5
			opd_Repository.save(dueUpdate); }
		if(form.getDept().equals("RADIO")) {
			ReceptionBillRadio_Entity dueUpdate = radio_Repository.findByBillNoAndCreditYear(form.getBillNo(),creditYear);
			dueUpdate.setPaidAmount(form.getAmount()); // 7
			dueUpdate.setDue(String.valueOf(Integer.valueOf(dueUpdate.getDue())-Integer.valueOf(form.getAmount()))); // 7-2=5
			radio_Repository.save(dueUpdate); }
		if(form.getDept().equals("HEALTHCHECKUP")) {
			ReceptionBillOpdHealthCheckup_Entity dueUpdate = healthCheckup_Repository.findByBillNoAndCreditYear(form.getBillNo(),creditYear);
			dueUpdate.setPaidAmount(form.getAmount()); // 7
			dueUpdate.setDue(String.valueOf(Integer.valueOf(dueUpdate.getDue())-Integer.valueOf(form.getAmount()))); // 7-2=5
			healthCheckup_Repository.save(dueUpdate); }
		
		entity = repository.save(entity);
		if (entity.getId() != 0) {
			response.setStatus(200);
			response.setType(ResponseType.SUCCESS);
			response.setMessage("Saved");
			return response;
		}
		return response;
	}

	public BaseResponse update(ReceptionBill_CashDues_DTO form, Principal principal) {
		BaseResponse response = new BaseResponse();
		ReceptionBill_CashDues_Entity entity = repository.findById(form.getId());
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
		ReceptionBill_CashDues_Entity entity = repository.findById(id);
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
	public BaseResponse findAll(Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByCreditYearOrderByIdDesc(creditYear));
		return response;
	}
	
	@ReadOnlyProperty
	public BaseResponse getCurrent(String date,Principal principal,Long creditYear) {
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(repository.findByReceiptDateAndCreditYearOrderByIdDesc(date,creditYear));
		return response;
	}

	@ReadOnlyProperty
	public BaseResponse getDue(Long creditYear) {
		List<PendingDueInterface> opd_dues = opd_Repository.getPendingDueList(creditYear);
		List<PendingDueInterface> radio_dues = radio_Repository.getPendingDueList(creditYear);
		List<PendingDueInterface> lab_dues = lab_Repository.getPendingDueList(creditYear);
		List<PendingDueInterface> healthcheckup_dues = healthCheckup_Repository.getPendingDueList(creditYear);
		List<PendingDueInterface> list = Stream.of(opd_dues, radio_dues, lab_dues, healthcheckup_dues).flatMap(Collection::stream).collect(Collectors.toList());
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage(" ");
		response.setBody(list);
		return response;
	}

	public byte[] report_PDF(String uhid, String format) {
		try {
			String templetSrc = env.getProperty("cash_due.report.file");
			ReceptionBill_CashDues_Entity dataList = repository.findById(Long.valueOf(uhid));
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("receiptNo", dataList.getReceiptDate());
			parameters.put("receiptDate", dataList.getReceiptDate());
			if(dataList.getType().equals("PLASTICMONEY")) {
				parameters.put("mode", dataList.getType()+" ("+dataList.getPlasticInstrumentName()+")");
			}else {
				parameters.put("mode", dataList.getType());
			}
			parameters.put("patientName", dataList.getAgainst());
			parameters.put("amount", dataList.getAmount());
			parameters.put("words", dataList.getWords());
			parameters.put("address", "NAVSARI,NAVSARI");
			parameters.put("uhid", dataList.getUhid());
			parameters.put("userName", dataList.getUserName());
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

		ArrayList<ReceptionBill_CashDues_Entity> array=new ArrayList<>();
		ReceptionBill_CashDues_Entity entiry = repository.findById(searchtext);
		if (entiry!=null) {
			array.add(entiry);
		}
		
		BaseResponse response = new BaseResponse();
		response.setStatus(200);
		response.setType(ResponseType.SUCCESS);
		response.setMessage("List");
		response.setBody(array);
		return response;
	}
	
}
