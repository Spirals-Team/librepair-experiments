package org.terasoluna.tourreservation.app.managereservation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfDownloadView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DownloadPDFOutput downloadPDFOutput = (DownloadPDFOutput) model.get("downloadPDFOutput");
				
		String pdfTemplateFilePath = this.getServletContext().getRealPath(
				"/WEB-INF/reports/reservationReport.pdf"); 
		FileInputStream template = new FileInputStream(pdfTemplateFilePath);
		
		PdfReader reader = new PdfReader(template);
		PdfImportedPage page = writer.getImportedPage(reader, 1); 
		PdfContentByte cb = writer.getDirectContent();
		cb.addTemplate(page, 0, 0);
		
		PdfStamper stamper= new PdfStamper(reader, new FileOutputStream("test.pdf"));
		AcroFields form = stamper.getAcroFields();
		form.setField("referenceName", downloadPDFOutput.getReferenceName());
		form.setField("referenceEmail", downloadPDFOutput.getReferenceEmail());
		form.setField("referenceTel", downloadPDFOutput.getReferenceTel());
		form.setField("paymentMethod", downloadPDFOutput.getPaymentMethod());
		form.setField("paymentCompanyName", downloadPDFOutput.getPaymentCompanyName());
		form.setField("paymentAccount", downloadPDFOutput.getPaymentAccount());
		form.setField("childCount", String.valueOf(downloadPDFOutput.getChildCount()));
		form.setField("tourName", downloadPDFOutput.getTourName());
		form.setField("accomName", downloadPDFOutput.getAccomName());
		form.setField("customerKana", downloadPDFOutput.getCustomerKana());
		form.setField("customerTel", downloadPDFOutput.getCustomerTel());
		form.setField("adultUnitPrice", String.valueOf(downloadPDFOutput.getAdultUnitPrice()));
		form.setField("reservedDay", String.valueOf(downloadPDFOutput.getReservedDay()));
		form.setField("conductor", downloadPDFOutput.getConductor());
		form.setField("tourAbs", downloadPDFOutput.getTourAbs());
		form.setField("customerAdd", downloadPDFOutput.getCustomerAdd());
		form.setField("customerJob", downloadPDFOutput.getCustomerJob());
		form.setField("tourDays", downloadPDFOutput.getTourDays());
		stamper.setFormFlattening(true);
		stamper.close();
		reader.close();

		




		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=test.pdf");
	}

}
