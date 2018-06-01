package com.economizate.servicios.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.economizate.servicios.BaseSheet;
import com.economizate.servicios.BaseWriter;

public class ExcelWriter extends BaseWriter {

	private BaseSheet hoja;

	public ExcelWriter(String nombreArchivo, BaseSheet hoja) {
		super(nombreArchivo);
		this.hoja = hoja;
	}

	@Override
	public void write() throws IOException, FileNotFoundException {		
		Workbook workbook = new XSSFWorkbook();
		hoja.createSheet(workbook);
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		workbook.write(fos);
		fos.close();

	}
}
