package com.as.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.as.h_entities.hims.BedMaster;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

@Component
public class BedReportGenerator {

	public ByteArrayInputStream generateBedReport(List<BedMaster> data) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		

		try {

			Set<String> wardNameList = new HashSet<>();

			data.stream().forEach(d -> wardNameList.add(d.getWardname()));

			PdfWriter writer = PdfWriter.getInstance(document, out);
			 
			PdfFooter event = new PdfFooter();
	        writer.setPageEvent(event);
			document.open();
			Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			fontHeader.setSize(14f);

			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			fontHeader.setSize(16f);

			Paragraph pdfHeader = new Paragraph(
					new Phrase("Orange Hospital Heart & Superspeciality Institute", fontHeader));

			pdfHeader.setAlignment(Element.ALIGN_CENTER);
			pdfHeader.setSpacingBefore(10f);
			document.add(pdfHeader);

			LineSeparator sep = new LineSeparator();
			sep.setOffset(-10);
			document.add(sep);

			Paragraph pdfLabel = new Paragraph(new Phrase("VACANT BEDS REPORT", font));
			pdfLabel.setAlignment(Element.ALIGN_CENTER);
			pdfLabel.setSpacingBefore(15f);
			document.add(pdfLabel);
			
			LineSeparator sep2 = new LineSeparator();
			sep2.setPercentage(70);
			sep2.setOffset(-10);
			document.add(sep2);

			Font fontValues = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			fontValues.setSize(8f);

			Font fontTableValues = FontFactory.getFont(FontFactory.HELVETICA);
			fontTableValues.setSize(8f);
			
			Paragraph bedNoLable = new Paragraph(new Phrase("BED NO", fontValues));
			Paragraph groupLabel = new Paragraph(new Phrase("GROUP", fontValues));
			Paragraph grandTotalLabel = new Paragraph(new Phrase("GRAND TOTAL", fontValues));
			Paragraph totalBedLabel = new Paragraph(new Phrase("TOTAL VACANT BED", fontValues));

			
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(70);
			table.setWidths(new int[] { 1, 1 });

			PdfPCell cellOne = new PdfPCell(bedNoLable);
			PdfPCell cellTwo = new PdfPCell(groupLabel);
			cellOne.setBorder(Rectangle.NO_BORDER);
			cellTwo.setBorder(Rectangle.NO_BORDER);
			table.addCell(cellOne);
			table.addCell(cellTwo);
			table.setSpacingBefore(20f);
			document.add(table);

			sep2.setOffset(-10);
			document.add(sep2);
			
			for (String wardName : wardNameList) {

				List<BedMaster> bedList = data.stream().filter(bed -> bed.getWardname().equalsIgnoreCase(wardName))
						.collect(Collectors.toList());

				
				Paragraph groupNameLabel = new Paragraph(new Phrase(wardName, fontValues));
			

				

				PdfPTable wardNameTable = new PdfPTable(1);
				wardNameTable.setWidthPercentage(70);
				wardNameTable.setWidths(new int[] { 1 });
				PdfPCell groupNameCell = new PdfPCell(groupNameLabel);
				groupNameCell.setBorder(Rectangle.NO_BORDER);
				wardNameTable.addCell(groupNameCell);
				wardNameTable.setSpacingBefore(20f);
				document.add(wardNameTable);
				
				PdfPTable dataTable = new PdfPTable(2);
				dataTable.setWidthPercentage(70);
				dataTable.setWidths(new int[] { 1, 1 });
				
				bedList.forEach(bedData -> {

					Paragraph bedNo = new Paragraph(new Phrase(bedData.getBedno(), fontTableValues));
					Paragraph group = new Paragraph(new Phrase(wardName, fontTableValues)); 
					try {
						

						PdfPCell dataCellOne = new PdfPCell(bedNo);
						PdfPCell dataCellTwo = new PdfPCell(group);
						dataCellOne.setBorder(Rectangle.NO_BORDER);
						dataCellTwo.setBorder(Rectangle.NO_BORDER);
						dataTable.addCell(dataCellOne);
						dataTable.addCell(dataCellTwo);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}); 
				
				document.add(dataTable);
				
				sep2.setOffset(-5);
				document.add(sep2);
			 
				PdfPTable grandTotalTable = new PdfPTable(3);
				grandTotalTable.setWidthPercentage(70);
				grandTotalTable.setWidths(new int[] { 2, 2, 1 });

 				Paragraph tatal = new Paragraph(new Phrase(""+bedList.size(),fontTableValues));
 				tatal.setAlignment(Element.ALIGN_CENTER);
				
				PdfPCell grandTotalCellOne = new PdfPCell(grandTotalLabel);
				PdfPCell grandTotalCellTwo = new PdfPCell(new Phrase());
				PdfPCell grandTotalCellThree = new PdfPCell(tatal);
				
				grandTotalCellOne.setBorder(Rectangle.NO_BORDER);
				grandTotalCellTwo.setBorder(Rectangle.NO_BORDER);
				grandTotalCellThree.setBorder(Rectangle.NO_BORDER);

				grandTotalTable.addCell(grandTotalCellOne);
				grandTotalTable.addCell(grandTotalCellTwo);
				grandTotalTable.addCell(grandTotalCellThree);

				
				grandTotalTable.setSpacingBefore(10f);
				document.add(grandTotalTable);
				sep2.setOffset(-5);
				document.add(sep2);

			}

			
			PdfPTable totalTable = new PdfPTable(3);
			totalTable.setWidthPercentage(70);
			totalTable.setWidths(new int[] { 2, 2, 1 });

				Paragraph tatal = new Paragraph(new Phrase(""+data.size(),fontTableValues));
				tatal.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell totalCellOne = new PdfPCell(totalBedLabel);
			PdfPCell totalCellTwo = new PdfPCell(new Phrase());
			PdfPCell totalCellThree = new PdfPCell(tatal);
			
			totalCellOne.setBorder(Rectangle.NO_BORDER);
			totalCellTwo.setBorder(Rectangle.NO_BORDER);
			totalCellThree.setBorder(Rectangle.NO_BORDER);

			totalTable.addCell(totalCellOne);
			totalTable.addCell(totalCellTwo);
			totalTable.addCell(totalCellThree);

			
			totalTable.setSpacingBefore(10f);
			document.add(totalTable);
			sep2.setOffset(-5);
			document.add(sep2);
			
			
			
			
			document.close();

		} catch (DocumentException ex) {

			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static class PdfFooter extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try {
            	Font fontTableValues = FontFactory.getFont(FontFactory.HELVETICA);
    			fontTableValues.setSize(8f);
            	DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            	Date date = new Date();
             
            	String formatedDate = df.format(date);
                Rectangle pageSize = document.getPageSize();
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(formatedDate,fontTableValues), pageSize.getLeft(50), pageSize.getBottom( 10), 0);
                ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase(String.format("%s", String.valueOf(writer.getCurrentPageNumber())),fontTableValues),
                        pageSize.getRight(50),  pageSize.getBottom(10), 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
