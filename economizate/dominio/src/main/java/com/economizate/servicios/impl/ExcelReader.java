package com.economizate.servicios.impl;

import java.io.File;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.economizate.servicios.BaseReader;

public class ExcelReader extends BaseReader {

	public ExcelReader(String nombreArchivo) {
		super(nombreArchivo);
	}
	
	@Override
	public String read() {
		StringBuilder resultado = new StringBuilder();
		try {
				
		        File f = new File( FileName );
		        Delimiter = ";";
		        Workbook wb = WorkbookFactory.create(f);
		        org.apache.poi.ss.usermodel.Sheet mySheet = wb.getSheetAt(0);
		        //Iterator<Row> rowIter = ((org.apache.poi.ss.usermodel.Sheet) mySheet).rowIterator();
		        for ( Iterator<Row> rowIterator = ((org.apache.poi.ss.usermodel.Sheet) mySheet).rowIterator() ;rowIterator.hasNext(); )
		        {
		            for (  Iterator<Cell> cellIterator = ((Row)rowIterator.next()).cellIterator() ; cellIterator.hasNext() ;  ) 
		            {		            	
		            	resultado.append(( (Cell)cellIterator.next() ).toString());
		                if(cellIterator.hasNext()) {
		                	resultado.append(Delimiter);	
		                }
		            }
		            resultado.append(System.getProperty("line.separator"));
		        }
		    } catch ( Exception e )
		    {
		        System.out.println( "exception" );
		        e.printStackTrace();
		    }
		return resultado.toString();
	}

	@Override
	public String toString() {
		return ParserType.EXCEL.toString();
	}
}
