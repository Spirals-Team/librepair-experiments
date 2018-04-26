package com.economizate.servicios;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.TransformerException;

import com.lowagie.text.DocumentException;

public interface BaseTransformador {

	public StringWriter procesar() throws IOException, DocumentException, TransformerException;
}
