package com.economizate.servicios.impl;

import java.io.File;
import java.io.IOException;
import javax.xml.transform.TransformerException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.economizate.servicios.BaseTransformador;
import com.economizate.servicios.BaseWriter;
import com.lowagie.text.DocumentException;

public class PdfWriter extends BaseWriter {

	private BaseTransformador trans;
	
	public PdfWriter(String nombreArchivo, BaseTransformador tranformador) {
		super(nombreArchivo);
		trans = tranformador;
	}

	@Override
	public void write() throws IOException {		
		
		try {
			FileUtils.writeByteArrayToFile(new File(nombreArchivo), toPdf(trans.procesar().toString()));
		} catch (DocumentException e) {
			e.getMessage();
		} catch (TransformerException e) {
			e.getMessage();
		}

	}

	private byte[] toPdf(String html) throws DocumentException, IOException {
		final ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(html);
		renderer.layout();
		try (ByteArrayOutputStream fos = new ByteArrayOutputStream(
				html.length())) {
			renderer.createPDF(fos);
			return fos.toByteArray();
		}
	}
}
