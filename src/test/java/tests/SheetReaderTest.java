package tests;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import reader.SheetReader;

public class SheetReaderTest extends TestCase {
	
	public static TestSuite LectorSolapasTests(){
		
		return new TestSuite(SheetReaderTest.class);
	}
	
	SheetReader instancia1;
	
	public SheetReaderTest() throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(new File("src/test/java/resources/Test3.xlsx"));
		instancia1 = new SheetReader(workbook);
	}
	
	public void test1() {
		assertEquals(0,instancia1.getSheets().size());
	}	
	
	public void test2() {
		assertEquals(true,instancia1.sheetExists("hoja1"));		
	}
	
	public void test3() {
		assertEquals(false,instancia1.sheetExists("hoja"));		
	}

}
