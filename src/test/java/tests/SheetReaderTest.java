package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import reader.SheetReader;
import reader.WorkbookCreator;

public class SheetReaderTest {

	SheetReader instancia1;

	@Before
	public void setUp() throws Exception {
		WorkbookCreator workbook = new WorkbookCreator("path1","sheet1");
		instancia1 = new SheetReader(workbook.getWorkbook());
		workbook.close();
	}

	@Test
	public void getSheetsTest1() {
		assertEquals(1, instancia1.getSheets().size());
	}

	@Test
	public void sheetExistTest1() {
		assertEquals(true, instancia1.sheetExists("Hoja 1"));
	}

	@Test
	public void sheetExistTest2() {
		assertEquals(false, instancia1.sheetExists("Hoja 2"));
	}

	@Test
	public void getSheetAtTest1() {
		assertEquals("Hoja 1", instancia1.getSheetAt(0).getSheetName());
	}

	@Test
	public void getSheetTest1() {
		assertEquals(instancia1.getSheetAt(0), instancia1.getSheet("Hoja 1"));
	}
	
	

}
