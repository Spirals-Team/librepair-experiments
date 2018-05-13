package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.simple.SpreadsheetDocument;

public class ODSReaderTest {

	/**
	 * Test to check that the method works as a cell (F4) is a diagonal border
	 */
	@Test
	public void testCellIsDiagonalBorder() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				ODSReader odsR = new ODSReader(sd);

				String yearOfStudy = "L3_Informatique";
				String cellPosition = "F4";

				Assert.assertTrue(odsR.isDiagonalBorder(yearOfStudy, cellPosition));

			}
		}
	}

	/**
	 * Test to check that the method works as a cell (I4) is not a diagonal border
	 */
	@Test
	public void testCellIsNotDiagonalBorder() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				ODSReader odsR = new ODSReader(sd);

				String yearOfStudy = "L3_Informatique";
				String cellPosition = "I4";

				Assert.assertFalse(odsR.isDiagonalBorder(yearOfStudy, cellPosition));
			}
		}
	}

	/**
	 * /** Test to check we can retrieve a cell value
	 */
	@Test
	public void testCellValueOK() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				String cellPosition = "I5";

				Assert.assertEquals("2", odsR.getCellValue(cellPosition));
			}
		}
	}

	/**
	 * Test to check we can retrieve a cell value
	 */
	@Test
	public void testCellValueNotOK() throws Exception {
		try (InputStream is = ODSReaderTest.class.getClassLoader().getResourceAsStream("Saisie_voeux_dauphine.ods")) {
			try (SpreadsheetDocument sd = SpreadsheetDocument.loadDocument(is)) {
				String yearOfStudy = "L3_Informatique";
				ODSReader odsR = new ODSReader(sd, yearOfStudy);

				String cellPosition = "I4";

				Assert.assertEquals("", odsR.getCellValue(cellPosition));
			}
		}
	}

}
