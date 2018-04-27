package com.economizate.servicios.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.economizate.entidades.MovimientoMonetario;
import com.economizate.servicios.BaseSheet;

public class MovimientosSheet implements BaseSheet {

	private List<MovimientoMonetario> movimientos;
	
	SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
	
	public MovimientosSheet(List<MovimientoMonetario> movimientos) {
		this.movimientos = movimientos;
	}
	
	@Override
	public void createSheet(Workbook workbook) {

		Sheet studentsSheet = workbook.createSheet("Movimientos");

		int rowIndex = 0;

		for (MovimientoMonetario mov : movimientos) {

			Row row = studentsSheet.createRow(rowIndex++);

			int cellIndex = 0;
			row.createCell(cellIndex++).setCellValue(formater.format(mov.getFecha()));
			row.createCell(cellIndex++).setCellValue(mov.getDescripcion());
			row.createCell(cellIndex++).setCellValue(mov.getObservacion());
			row.createCell(cellIndex++).setCellValue(mov.getImporte());
			//row.createCell(cellIndex++).setCellValue(mov.getCantidadCuotas());

		}
		
	}

	
}
