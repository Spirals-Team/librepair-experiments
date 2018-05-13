package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeachSpreadsheetDocument {

	private final static Logger LOGGER = LoggerFactory.getLogger(TeachSpreadsheetDocument.class);

	/**
	 * This method open a ODS file specified in the argument, and display the text
	 * which is in the cell and table specified in the argument. The method is using
	 * ODFToolKit from Apache.
	 * 
	 * @param fileName
	 *            file should be in the resources folder
	 * @param cellPosition
	 *            the name of the cell that we want to display
	 * @param tableName
	 *            the name of the table where the cell is
	 * 
	 * @author samuelcohen75 and fontlo15
	 * @see librarySource https://incubator.apache.org/odftoolkit/
	 */
	public static void openODS(String fileName, String tableName, String cellPosition)
			throws ClassCastException, FileNotFoundException, IOException, NullPointerException, Exception {
		try (InputStream inputStream = TeachSpreadsheetDocument.class.getClassLoader().getResourceAsStream(fileName)) {
			if (inputStream == null) {
				LOGGER.error("File " + fileName + " not found.");
				throw new FileNotFoundException("File not found");
			}

			LOGGER.info("File " + fileName + " has been loaded.");
			try (SpreadsheetDocument spreadSheetDocument = SpreadsheetDocument.loadDocument(inputStream)) {

				LOGGER.info("File " + fileName + " has been opened.");

				Cell positionCell = null;
				try {
					positionCell = spreadSheetDocument.getTableByName(tableName).getCellByPosition(cellPosition);
				} catch (NullPointerException ex) {
					LOGGER.error("Table " + tableName + " not found");
					throw ex;
				}

				if (positionCell == null) {
					LOGGER.warn("Cell " + cellPosition + " not found");
				} else {
					LOGGER.info("Cellule found, text in it is : " + positionCell.getDisplayText());
				}
			}
		}
		LOGGER.info("File " + fileName + " has been closed.");

	}
}
