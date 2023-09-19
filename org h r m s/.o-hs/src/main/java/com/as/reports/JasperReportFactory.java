package com.as.reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Component
public class JasperReportFactory {

	public byte[] getPdfFormat(String templetSrc, String fromat, Map<String, Object> parameters, List<?> fieldList) {
		JRBeanCollectionDataSource jrBeanCollectionDataSource = null;
		try {
			
			/* Template File Path Not Found */
			if(templetSrc == null) { System.err.println("JasperReportFactory: Template Path Not Found !"); return null; }
			ClassPathResource cpr = new ClassPathResource(templetSrc);

			/* Empty parameters And fieldList Handling */
			if (parameters == null) { parameters = new HashMap<>(); parameters.put("empty", "empty"); }
			if (fieldList == null || fieldList.isEmpty()) { ArrayList<String> local = new ArrayList<>(); local.add("Empty"); jrBeanCollectionDataSource = new JRBeanCollectionDataSource(local);
			} else { 
				jrBeanCollectionDataSource = new JRBeanCollectionDataSource(fieldList); 
			}

			
			
			JasperReport jasperReport = JasperCompileManager.compileReport(cpr.getInputStream());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
			
			if(fromat == null) { System.err.println("JasperReportFactory: Print Format Not Specified !"); return null; }
			if (fromat.equals("pdf")) {
				return JasperExportManager.exportReportToPdf(jasperPrint);
			}
			if (fromat.equals("xlsx")) {
				final JRXlsxExporter xlsxExporter = new JRXlsxExporter();
				try (final ByteArrayOutputStream xlsReport = new ByteArrayOutputStream()) {
					xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsReport));
					
					SimpleXlsxReportConfiguration conf = new SimpleXlsxReportConfiguration ();
					conf.setWhitePageBackground (false);
					conf.setDetectCellType (true);
					conf.setShowGridLines(true);
					conf.setRemoveEmptySpaceBetweenRows(true);
					conf.setRemoveEmptySpaceBetweenColumns(true);
					xlsxExporter.setConfiguration (conf);
					
					
					xlsxExporter.exportReport();
					return xlsReport.toByteArray();
				} catch (JRException | IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("JasperReportFactory: Please Debug Code !");
		return null;
	}

}
