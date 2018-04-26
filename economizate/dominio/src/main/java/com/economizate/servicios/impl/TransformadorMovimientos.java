package com.economizate.servicios.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseTransformador;
import com.lowagie.text.DocumentException;

public class TransformadorMovimientos implements BaseTransformador {

	private List<MovimientoMonetario> movimientos;
	
	public TransformadorMovimientos(List<MovimientoMonetario> movimientos) {
        this.movimientos = movimientos;
    }
	
	@Override
	public StringWriter procesar() throws IOException, DocumentException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xls = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream("Movimientos.xsl"));
        Transformer newTransformer = factory.newTransformer(xls);
        String str = ConvertObjetoToXML.convert(movimientos);
        StreamSource xml = new StreamSource(new StringReader(str));
        StringWriter writer = new StringWriter();
        newTransformer.transform(xml, new StreamResult( writer));

       return writer;            

		
	}
}
