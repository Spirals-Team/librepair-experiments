package reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SheetReader {
	private Workbook workbook;
	
	public SheetReader(Workbook workbook) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this.workbook = workbook;
	}
	
	public List<Sheet> getSheets(){
		List<Sheet> sheets = new ArrayList<>();
		for(Sheet sheet : workbook) {
			sheets.add(sheet);
		}
		return sheets;
	}
	
	public Sheet getSheet(String name) {
		return workbook.getSheet(name);
	}

	public Sheet getSheetAt(int index) {
		return workbook.getSheetAt(index);
	}
	
	public boolean sheetExists(String name) {
		for(Sheet sheet: workbook) {
			if(sheet.getSheetName().equals(name))
				return true;
		}
		return false;
	}
		
}
