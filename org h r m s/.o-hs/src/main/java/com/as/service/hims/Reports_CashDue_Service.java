package com.as.service.hims;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.as.reporsitories.hims.ReceptionBillLab_Repository;
import com.as.reporsitories.hims.ReceptionBillOpd_Repository;
import com.as.reporsitories.hims.ReceptionBillRadio_Repository;
import com.as.reports.JasperReportFactory;
import com.as.response.PendingDueInterface;

@Service
public class Reports_CashDue_Service {

	@Autowired
	JasperReportFactory factory;

	@Autowired
	Environment env;

	@Autowired
	ReceptionBillOpd_Repository opd_Repository;

	@Autowired
	ReceptionBillRadio_Repository radio_Repository;

	@Autowired
	ReceptionBillLab_Repository lab_Repository;

	public byte[] report_PDF(String format,Long creditYear) {
		try {
			
			List<PendingDueInterface> opd_dues = opd_Repository.getPendingDueList(creditYear);
			List<PendingDueInterface> lab_dues = lab_Repository.getPendingDueList(creditYear);
			List<PendingDueInterface> radio_dues = radio_Repository.getPendingDueList(creditYear);
			List<PendingDueInterface> list = Stream.of(opd_dues, radio_dues, lab_dues).flatMap(Collection::stream).collect(Collectors.toList());
			if(list.isEmpty())return null;
			Integer due=opd_Repository.getSumOfDue()+radio_Repository.getSumOfDue()+lab_Repository.getSumOfDue();
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("due", String.valueOf(due));
			String templetSrc = env.getProperty("reports.Report_CashDue.file");
			return factory.getPdfFormat(templetSrc, format, parameters, list);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
