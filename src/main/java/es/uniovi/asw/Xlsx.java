package es.uniovi.asw;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.Agente;

public class Xlsx {


	public static ArrayList<Agente> leerAgentes(ArrayList<Agente> agentes, String ruta) {
		try {
			FileInputStream file = new FileInputStream(new File(ruta));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				ArrayList<Object> aux = new ArrayList<Object>();
				for (int i = 0; i <5; i++) {					
					aux.add(row.getCell(i) != null ? row.getCell(i).toString() : "");
				}

				//String nombre = row.getCell(0) != null ? row.getCell(0).toString() : null;
				//if (nombre != null && nombre.equals("Nombre"))
				//	continue;

				int tipo= (int) Double.parseDouble(aux.get(4).toString());
				String localizacion="";
				if(!aux.get(1).equals(null))
				{
					localizacion=aux.get(1).toString();
				}
					
				Agente agente = new Agente(aux.get(0).toString(),localizacion , aux.get(2).toString(),aux.get(3).toString(), tipo);
				agentes.add(agente);
				
			}

			file.close();
			workbook.close();
		} catch (Exception e) {
			System.err.println("Error al leer del excel xlsx Mensaje: "+ e.getMessage());
		}
		return agentes;
	}


}