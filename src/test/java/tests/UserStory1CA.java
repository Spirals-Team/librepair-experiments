package tests;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

import reader.SheetReader;

public class UserStory1CA {

	SheetReader instancia1;
	String blocNotas = "assignment/resources/BlocDeNotas.txt";
	String hojaSimple = "assignment/resources/hojaSimple.xlsx";
	String noExcel = "assignment/resources/noSoyUnExcel.xlsx";	
	
	@Test // Agregar que los lea del .config
	public void testA() {
		try {
			Workbook workbook;
			try {
				workbook = WorkbookFactory.create(new File(hojaSimple));
				workbook.close();
				assert (false);
			} catch (EncryptedDocumentException e) {
				assert (false);
			} catch (InvalidFormatException e) {
				assert (false);
			}
		} catch (IOException e) {
			assert (true);
		}

		// instancia1 = new SheetReader(workbook);
	}	
	
	@Test
	public void testB() {
		try {
			Workbook workbook;
			try {
				workbook = WorkbookFactory.create(new File(blocNotas));
				workbook.close();
				assert (false);
			} catch (EncryptedDocumentException e) {
				assert (false);
		} catch (IOException e) {
			assert (true);
		}
		} catch (InvalidFormatException e) {
			assert (false);
		}
	}

	@Test
	public void testC() {
		try {
			Workbook workbook;
			try {
				workbook = WorkbookFactory.create(new File(blocNotas));
				workbook.close();
				assert (false);
			} catch (EncryptedDocumentException e) {
				assert (false);
		} catch (IOException e) {
			assert (true);
		}
		} catch (InvalidFormatException e) {
			assert (false);
		}		
	}
	
}
