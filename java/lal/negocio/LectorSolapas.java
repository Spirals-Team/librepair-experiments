package lal.negocio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class LectorSolapas {
	Workbook workbook;
	
    public static final String nombreArchivo = "prueba1.xlsx";

	public LectorSolapas(Workbook workbook) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this.workbook = workbook;
	}
	
	public List<String> getSolapas(){
		List<String> solapas = new ArrayList<String>();
		for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }
		return solapas;
	}
	
	public boolean existeSolapa(String nombre) {
		for(Sheet sheet: workbook) {
			if(sheet.getSheetName().equals(nombre))
				return true;
		}
		return false;
	}
		
}
