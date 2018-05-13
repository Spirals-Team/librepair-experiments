package io.github.oliviercailloux.y2018.teach_spreadsheets.odf;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Table;

/**
 * <b>This interface allows to read data from a spreadsheet.</b>
 * 
 * @author tuannamdavaux, samuelcohen75
 * @see io.github.oliviercailloux.y2018.teach_spreadsheets.odf.SpreadsheetShower
 */
public interface SpreadsheetReader {

	/**
	 * get cell value at cellPosition
	 */
	public String getCellValue(String cellPosition);

	/**
	 * detect if there is a diagonal border in cell at cellPosition
	 * 
	 * @param cellPosition
	 *            for instance : B14
	 */
	public boolean isDiagonalBorder(String yearOfStudy, String cellPosition);

	/**
	 * detect if there is a diagonal border in cell at cellPosition
	 * 
	 * @param cell
	 */
	public boolean isDiagonalBorder(Cell cell);

	/**
	 * specify a current sheet
	 */
	public void setSheet(Table sheet);

	/**
	 * specify the input file
	 */
	public void setDocument(SpreadsheetDocument document);

}
